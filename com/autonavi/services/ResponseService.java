package com.autonavi.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.dto.rsp.DownLoadRspDto;
import com.autonavi.httpactions.DownLoadAction;

@SuppressWarnings("unused")
public class ResponseService {

	/**
	 * @param 处理下载请求的response:
	 * @note1 {"statuscode":"1","statusmsg":"error"}
	 * @note2 {"outJson":{"size":0,"data":[]},"statuscode":"0","statusmsg":"success"}
	 */
	static Logger logger = Logger.getLogger(ResponseService.class); //ResponseService为类名
	
	static String fileEncoding = "";
	
	public static List<DownLoadRspDto> getResponseList(JSONObject obj){
		
		List<DownLoadRspDto> downloadrsp_list = new ArrayList<DownLoadRspDto>();
		
		JSONObject rsp = obj;
		JSONArray jsonArray = new JSONArray();
		int arrayLength = 0;
		fileEncoding = "UTF-8";
		
		try {
			// statuscode状态0正常，1异常 
			String statuscode = rsp.getString("statuscode");
			String statusmsg  = rsp.getString("statusmsg");
			
			if(statuscode.equals("0")){
				
				JSONObject outJson = rsp.getJSONObject("outJson");
				
				int size = outJson.getInt("size");
				
				if(size > 0){
					
					jsonArray = outJson.getJSONArray("data");
					arrayLength = jsonArray.length();
							
					for(int i=0;i<arrayLength;i++){
						JSONObject data = new JSONObject();
						data = (JSONObject) jsonArray.get(i);
						
						String title = data.getString("title");
						String description = data.getString("description");
						String id = data.getString("id");
						int status = Integer.parseInt(data.getString("status"));
						String current_user = data.getString("current_user");
						String points = data.getString("points");
						int aosid = Integer.parseInt(data.getString("aosid"));
						
						String operate_time = "";
						String operate_user = "";
						String comment = "";
						String operate_remark = "";
						
						JSONArray operate = new JSONArray();						
						operate = data.getJSONArray("operate");
						if(operate.length() != 0){
							
							JSONObject operate_end = (JSONObject) operate.get(0);														
							operate_time = operate_end.getString("operate_time");
							operate_user = operate_end.getString("operate_user");
							if(operate_end.get("comment") != null ){
								comment = operate_end.getString("comment");
							}else{
								;
							}
							operate_remark = operate_end.getString("operate_remark");
							/**							
							logger.info("ResponseService getResponseList operate_end: "+ title+","+comment);	
							
							if(comment.equals("") || comment == null){
								JSONObject operate_end_1 = (JSONObject) operate.get(operate.length() - 1);
								operate_time = operate_end_1.getString("operate_time");
								operate_user = operate_end_1.getString("operate_user");
								comment = operate_end_1.getString("comment");
								operate_remark = operate_end_1.getString("operate_remark");
								
								logger.info("ResponseService getResponseList comment: "+ comment);	
								
							}
							**/
						}						
						
						DownLoadRspDto rspDto = new DownLoadRspDto();
						rspDto.setAdamID(id);
						rspDto.setAOSID(aosid);
						rspDto.setStatus(status);
						rspDto.setTitle(title);
						rspDto.setDescription(description);
						rspDto.setPoints(points);
						rspDto.setReply(operate_user);
						rspDto.setReplyDate(operate_time);
						if(comment.equals("") || comment == null){
							rspDto.setReplyComment(operate_remark);
						}else{
							rspDto.setReplyComment(comment);
						}
												
						downloadrsp_list.add(rspDto);					
					}
					
				}else{
					return downloadrsp_list;
				}
				
			}else{
				logger.info("ResponseService getResponseList 请求参数返回错误: "+ statusmsg);	
				return downloadrsp_list;
			}
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("ResponseService getResponseList Error: "+ e);	
			return downloadrsp_list;
		}
		
		return downloadrsp_list;
	}
	
	public static void main(String[] args){
		
		JSONObject rsp = new JSONObject();
		
		try {
			
			//rsp.put("id",);
			String id = null;
			
			System.out.println(rsp.getString("id"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
