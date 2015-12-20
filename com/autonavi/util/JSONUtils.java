package com.autonavi.util;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.services.RecordIDService;

public class JSONUtils {

	/**
	 * @param jsonobject
	 */
	static Logger logger = Logger.getLogger(JSONUtils.class); //JSONUtils为类名
	
	public static JSONObject getJsonObjectFromMap(HashMap<String,String> map_temp,int state){
		
		// 待处理数据
		// 2015年7月15日新增数据
		String fid = "";
		String processing_start_time = "";
		
		// 原始数据
		String origin_lat = "";
		String origin_lon = "";
		String dest_lat = "";
		String dest_lon = "";
		String status_message = "";		
		
		// 2015年7月15日新增数据
		String dest_street_name = "";
		String dest_city = "";
		String dest_state = "";
		
		// 传递的1个参数
		String points = "";
		
		// OnStar_ID
		String title = "";
		String repeat = "";
		
		if(state == 0){
			// 表示数据不是重复数据
			title = "/";
			repeat = "1";
		}else if(state == 1){
			// 重复不提交问题，需赋值title
			title = RecordIDService.getRecordNo();
			repeat = "0";
		}
		 
		HashMap<String,String> map = map_temp;
		
		@SuppressWarnings("rawtypes")
		Iterator it = map.keySet().iterator();  
		while(it.hasNext()) {  
			 String key = (String)it.next();  
	        
			 if(key.equals("origin_lat")){
				 origin_lat = map.get(key);
			 }else if(key.equals("origin_lon")){
				 origin_lon = map.get(key);
			 }else if(key.equals("dest_lat")){
				 dest_lat = map.get(key);
			 }else if(key.equals("dest_lon")){
				 dest_lon = map.get(key);
			 }else if(key.equals("status_message")){
				 status_message = map.get(key);
			 }else if(key.equals("fid")){
				 fid = map.get(key);
			 }else if(key.equals("processing_start_time")){
				 processing_start_time = map.get(key);
			 }else if(key.equals("dest_street_name")){
				 dest_street_name = map.get(key);
			 }else if(key.equals("dest_city")){
				 dest_city = map.get(key);
			 }else if(key.equals("dest_state")){
				 dest_state = map.get(key);
			 }	  	  	  	    	       	       
		}				
		
		points = origin_lon+","+origin_lat+"|"+dest_lon+","+dest_lat;
		
		JSONObject obj = new JSONObject();
		try{
			obj.put("repeat",repeat);	
			obj.put("fid",fid);	
			obj.put("processing_start_time",processing_start_time);	
			
			if(dest_street_name.equals("")){
				obj.put("dest_street_name","/");	
			}else{
				obj.put("dest_street_name",dest_street_name);	
			}
			
			if(dest_city.equals("")){
				obj.put("dest_city","/");	
			}else{
				obj.put("dest_city",dest_city);	
			}
			
			if(dest_state.equals("")){
				obj.put("dest_state","/");	
			}else{
				obj.put("dest_state",dest_state);	
			}			
			
			obj.put("adamid","/");
			obj.put("aosid","/");
			//obj.put("add_date",add_date);
			obj.put("description",status_message);
			obj.put("title",title);
			obj.put("points",points);
			//obj.put("submitter",submitter);
			obj.put("reply","/");
			obj.put("reply_date","/");
			obj.put("reply_comment","/");
		}catch (JSONException e){					
			logger.info("JSONUtils.getJsonObjectFromMap error: "+ e);
			return null;
		}
		
		return obj;
	}

}
