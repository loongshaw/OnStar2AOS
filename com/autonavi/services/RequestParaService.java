package com.autonavi.services;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;
import com.autonavi.util.DateUtils;
import com.autonavi.util.Md5Utils;
import com.autonavi.util.UUIDUtils;


public class RequestParaService {

	/**
	 * @param 处理http post参数问题
	 */
	static Logger logger = Logger.getLogger(RequestParaService.class); //RequestParaService为类名
	
	public static String getRequestParameter(HashMap<String,String> map_temp){
		String para = "";
		// 待处理数据
		String origin_lat = "";
		String origin_lon = "";
		String dest_lat = "";
		String dest_lon = "";
		String status_message = "";
		// 传递的3个参数
		String title = "";
		String points = "";
		String description = "";
						
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
	        }	       
	    }  
		
	    JSONObject descJson = new JSONObject();
	    try {	    	
			descJson.put("uDes", status_message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("getRequestParameter descJson: "+ e);
			// 参数处理
		    title = RecordIDService.getRecordNo();
		    points = origin_lon+","+origin_lat+"|"+dest_lon+","+dest_lat+"||"+dest_lon+","+dest_lat;
		    description = e.toString();
		    
		    StringBuffer base_sb = getBaseRequestParameter();
		    base_sb.append("title=" + title);
		    base_sb.append("&");
		    base_sb.append("points=" + points);
		    base_sb.append("&");
		    base_sb.append("description=" + description);
		    base_sb.append("&");
		    base_sb.append("sign=" + Md5Utils.getSignStr(description));
		    
		    para = base_sb.toString();
		    
			return para;
		}
	    // 参数处理
	    title = RecordIDService.getRecordNo();
	    points = origin_lon+","+origin_lat+"|"+dest_lon+","+dest_lat+"||"+dest_lon+","+dest_lat;
	    description = descJson.toString();
	    
	    StringBuffer base_sb = getBaseRequestParameter();
	    base_sb.append("title=" + title);
	    base_sb.append("&");
	    base_sb.append("points=" + points);
	    base_sb.append("&");
	    base_sb.append("description=" + description);
	    base_sb.append("&");
	    base_sb.append("sign=" + Md5Utils.getSignStr(description));
	    
	    para = base_sb.toString();
	    
		return para;
	}
	
	public static StringBuffer getBaseRequestParameter(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("channel=" + Constants.CHANNEL);
		sb.append("&");
		sb.append("dip=" + Constants.dip);
		sb.append("&");
		sb.append("type=" + Constants.type);
		sb.append("&");
		sb.append("diu=" + UUIDUtils.getUUID());
		sb.append("&");
		sb.append("output=" + "json");
		sb.append("&");
		
		return sb;
	}
	
	// down参数：
	/**
	starTime：开始日期
	endTime：结束日期
	gstate：工单状态(必须置空)
	pageNum：当前页
	pageSize ：页长
	getUser：用户名
	mkey：加密后的参数
	**/
	public static String getDownRequestParameter(int page,int day){
		String request = "";
		
		String starTime = "";
		String endTime = "";
		String gstate = "";
		int pageNum = page;
		int pageSize = 10;
		String getUser = "OnStar";
		String mkey = "";
		
		mkey = Md5Utils.getSignStr();
		
		StringBuffer sb = new StringBuffer();
		
		// String current_date = DateUtils.getCurrentDate2();		
		String current_date = DateUtils.getDate(day,1);		
		starTime = current_date+" 00:00:01";
		endTime = current_date+" 23:59:59";
		
		sb.append("starTime=" + starTime);
		sb.append("&");
		sb.append("endTime=" + endTime);
		sb.append("&");
		sb.append("gstate=" + gstate);
		sb.append("&");
		sb.append("pageNum=" + pageNum);
		sb.append("&");
		sb.append("pageSize=" + pageSize);
		sb.append("&");
		sb.append("getUser=" + getUser);
		sb.append("&");
		sb.append("mkey=" + mkey);
		
		request = sb.toString();
		
		return request;
	}
	
}
