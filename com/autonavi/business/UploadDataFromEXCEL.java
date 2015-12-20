package com.autonavi.business;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autonavi.constants.Constants;
import com.autonavi.dto.rsp.UploadRspDto;
import com.autonavi.httpactions.UploadAction;
import com.autonavi.services.ExcelService;
import com.autonavi.services.ReSchedulingService;
import com.autonavi.services.ReadExcelService;
import com.autonavi.services.RefreshFileListService;
import com.autonavi.util.FileUtils;
import com.autonavi.util.JSONUtils;

public class UploadDataFromEXCEL {

	/**
	 * @note 业务：将EXCEL数据上传至AOS
	 * @note 异常：提交不成功的记录处理
	 * @note 补充：1、将输入文档直接输出到输出文档。
	 */
	static Logger logger = Logger.getLogger(UploadDataFromEXCEL.class); //UploadDataFromEXCEL为类名
	
	public static void doBusiness(){
		
		List<String> filelist = RefreshFileListService.getFileList(Constants.filePath_Production);
		
		if(FileUtils.createFile(Constants.filePath_Txt) == true){
			logger.info("UploadDataFromEXCEL.doBusiness file create result: "+ "OK");
		}
		
		if(FileUtils.createFile(Constants.filePath_Txt_CountRecord) == true){
			logger.info("UploadDataFromEXCEL.doBusiness file create result: "+ "OK");
		}
		
		for(int i = 0; i < filelist.size(); i++)  
        {  
			String filepath = filelist.get(i);
			
			executeUpload(filepath);						
        }
	}
	
	
	public static void executeUpload(String filepath){
		boolean isWritenSuccess = false;	
		long time_start = System.currentTimeMillis(); 
		File file = new File(filepath);
		String fileName = file.getName();
		// 是否进行重命名：0 否,1是。
		int weatherRename = 1;
		int counts = 0;
		// 包含输入文档中的所有字段内容(原封不动)
		List<HashMap<String,String>> listmap = ReadExcelService.readExcel(file);
		
		if(listmap != null){
			
			JSONArray jsonArray = new JSONArray();
			
			for(int i = 0; i < listmap.size(); i++)  
	        {  								
				HashMap<String,String> map = listmap.get(i);  
				
				// 添加判断：排重
				boolean compared = ReSchedulingService.compareWith(map);
				// 没有重复，则进行提交！
				if(compared == false){
					UploadRspDto uploadrsp = UploadAction.uploadInfo(map);
					
					// 提交结果：0成功、1失败
					int result = uploadrsp.getCode();
					if(result == 1){
						weatherRename = 0;
						logger.info("UploadDataFromEXCEL.executeUpload error: "+ uploadrsp.getMessage());
					} else {
						// 记录成功上传的记录数
						counts++;
						// 提交成功后，进行txt文件写入
						boolean submit = ReSchedulingService.writeTxt(map);
						if(submit == false){							
							logger.info("UploadDataFromEXCEL.executeUpload error: "+ "写入TXT失败！");
						}
					}
					
					// 没有重复数据直接写入输出表格
					JSONObject jsonObj = JSONUtils.getJsonObjectFromMap(map,0);
					// 添加重复数据
					if(jsonObj != null){
						jsonArray.put(jsonObj);
					}					
					
				} else {
					// 若已有重复记录，则直接写入输出表格
					logger.info("UploadDataFromEXCEL.executeUpload result: "+ "已有重复记录！不再提交！");
					// 输出栏目(输入数据为：HashMap<String,String> map)
					JSONObject jsonObj = JSONUtils.getJsonObjectFromMap(map,1);
					// 添加重复数据
					if(jsonObj != null){
						jsonArray.put(jsonObj);
					}					
				}
						
	        }
			
			// 判断是否已存在指定输出文档
			boolean isExist = FileUtils.fileExists(FileUtils.getOutPutFilePath(0));
			if(isExist == true){
				;
			}else{
				// 所有数据写入输出文档
				if(jsonArray.length() > 0){
					// 所有数据写入当天的输出表格中
					isWritenSuccess = ExcelService.writeExcelTable(jsonArray,0);
					if(isWritenSuccess == false){
						logger.info("UploadDataFromEXCEL.executeUpload Writen Reslut: "+ isWritenSuccess);
					}
					logger.info("UploadDataFromEXCEL.executeUpload 输出原始数据量: "+ jsonArray.length());
				}
			}
		
			// 记录成功上传的条数
			if(counts > 0){
				logger.info("UploadDataFromEXCEL.executeUpload 成功上传记录条数: "+ counts);
			}
			// 文件处理完后，重命名:如果出现提交失败，则不进行重命名！
			if(weatherRename == 1){
				RefreshFileListService.renameFile(filepath);
			}			
		}
		long time_end = System.currentTimeMillis(); 
		logger.info("UploadDataFromEXCEL.executeUpload----FileName:"+fileName +"----Spend time:"+ (time_end-time_start));		
	}
	
	/**
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		executeUpload("D://OnStar_2015071301.xlsx");
		// doBusiness();
	}
	**/
	
}
