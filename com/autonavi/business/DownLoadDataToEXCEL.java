package com.autonavi.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.autonavi.constants.Constants;
import com.autonavi.dto.rsp.DownLoadRspDto;
import com.autonavi.httpactions.DownLoadAction;
import com.autonavi.services.ExcelService;
import com.autonavi.services.ReSchedulingService;
import com.autonavi.util.FileUtils;


public class DownLoadDataToEXCEL {

	/**
	 * @note 业务：将adma数据下载输出至EXCEL
	 * @note 逻辑：1、请求获取反馈结果；2、将反馈结果写入EXCEL
	 */	
	static Logger logger = Logger.getLogger(DownLoadDataToEXCEL.class); //DownLoadDataToEXCEL为类名
	
	public static void doBusiness(){
		
		if(FileUtils.createFile(Constants.filePath_Txt_AOS) == true){
			logger.info("DownLoadDataToEXCEL.doBusiness file create result: "+ "OK");
		}
		
		// 每天请求一圈最近一月的数据
		for(int i = 0; i < 15; i++){
			
			logger.info("DownLoadDataToEXCEL.doBusiness 查询前"+i+"天数据");
			
			for(int j = 0; j< Constants.MaxPage ; j++){
				
				int result = executeDownload(j,i);
				
				if(result == 0){
					// 重新以之前的page再执行一次
					j--;
				}else if(result == 1){
					// 表示输出结果小于10个,则无需进一步分页查询,退出循环
					logger.info("DownLoadDataToEXCEL.doBusiness 已查询页码"+j);
					break;
				}else if(result == 2){
					;
				}else if(result == 3){
					// 表示没有输出结果,退出循环
					break;
				}						
			}		
		}		
		
	}
	
	// 返回执行结果：0表示重新执行一次,1表示只执行一次,2表示继续执行,页码递增
	public static int executeDownload(int page,int day){
		
		boolean isWritenSuccess = false;
		// 获取反馈结果,list返回结果不是null
		List<DownLoadRspDto> list = DownLoadAction.downLoadInfo(page,day);
		int list_size = list.size();
		// 处理反馈结果：将list转成JsonArray
		if( list_size > 0){
			List<HashMap<String,String>> listmap = new ArrayList<HashMap<String,String>>();
			// JSONArray jsonArray = new JSONArray();
			for(int i=0;i <list.size();i++ ){
				DownLoadRspDto rsp = list.get(i);
				
				String adamid = rsp.getAdamID();						// ADAMID
				int aosid = rsp.getAOSID();								// AOSID
				int status = rsp.getStatus();
				//Date add_date = rsp.getDate();						// 提交时间
				String description = rsp.getDescription();				// 问题描述信息
				String title = rsp.getTitle();							// 主题(OnStarID)
				String points = rsp.getPoints();						// 坐标点集合
				//String submitter = rsp.getSubmitter();
				String reply = rsp.getReply();							// 回复对象
				String reply_date = rsp.getReplyDate();					// 回复时间
				String reply_comment = rsp.getReplyComment();			// 回复内容
				
				HashMap<String,String> obj = new HashMap<String,String>();
				//JSONObject obj = new JSONObject();
				try{				
					obj.put("adamid",adamid);
					obj.put("aosid",aosid+"");
					//obj.put("add_date",add_date);
					obj.put("description",description);
					obj.put("title",title);
					obj.put("points",points);
					//obj.put("submitter",submitter);
					obj.put("reply",reply);
					obj.put("reply_date",reply_date);
					obj.put("reply_comment",reply_comment);
					}catch (Exception e){					
						logger.info("DownLoadDataToEXCEL.executeUpload error: "+ e);
						return 0;
					}
			
				if(status == 7 || status == 2){
					// 添加判断：排重
					boolean compared = ReSchedulingService.compareWithTXT(aosid);
					// 没有重复，则进行写入！
					if(compared == false){
					 					
						listmap.add(obj);	
						
						// 没有重复，进行txt文件写入
						boolean submit = ReSchedulingService.writeDownTxt(aosid);			
						if(submit == false){
							logger.info("DownLoadDataToEXCEL.executeUpload Writen Reslut: "+ submit);
						}		
					}											
				}			
			}
		
			// 如果JSONArray非空，则进行EXCEL更新操作
			if(listmap.size() > 0){
				isWritenSuccess = ExcelService.updateExcelTable(listmap,day,"",1);
				if(isWritenSuccess == false){
					logger.info("DownLoadDataToEXCEL.executeUpload Writen Reslut: "+ isWritenSuccess);
				}
				logger.info("DownLoadDataToEXCEL.executeUpload 输出更新数据量: "+ listmap.size());
			}
		
			if(list_size < 10){
				return 1;
			}else{
				return 2;
			}		
		}else{
			return 3;
		}
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		// executeUpload("D://OnStar_2015042003.xlsx");
//		doBusiness();
//	}


}
