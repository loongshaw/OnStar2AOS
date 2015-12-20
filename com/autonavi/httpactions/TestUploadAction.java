package com.autonavi.httpactions;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.dto.base.BaseDtoRsp;
import com.autonavi.dto.rsp.UploadRspDto;
import com.autonavi.httpactions.base.ReqBaseAction;

public class TestUploadAction {

	/**
	 * @param args
	 */
	
	static Logger logger = Logger.getLogger(UploadAction.class); //UploadAction为类名
	public final static String UpLoad_HOST = "http://10.17.131.24:8070/verify/recieve.do"; 
	//public final static String UpLoad_HOST = "http://10.61.192.11:8082/user"; 
	//public final static String UpLoad_HOST = "https://login.alibaba-inc.com/authorize/login.do"; 
	
	@SuppressWarnings("rawtypes")
	public static UploadRspDto uploadInfo(){
		UploadRspDto uploadrsp = new UploadRspDto();
		
		try{
			// ReqBaseAction.Status = 0;
			String requestPara = "key=A5E13AC7-8055-47D9-912D-39099BADB731&data="+getArray().toString();	
			//String requestPara = "1";	
			//String requestPara = "source=2000&data="+getArray1().toString();	
			//String requestPara = "appcode=ac08e4444e9c44c1b7bb5e50a49eb548&name=xiaolong.xiao&password=loongshaw%26_1988&authtype=user";
			logger.info("UploadAction Upload RequestPara: "+ requestPara);	
			BaseDtoRsp  dtoRsp = new ReqBaseAction().doHttpForObject(requestPara,0,UpLoad_HOST);		
			logger.info("DtoRsp Result: "+ dtoRsp);	
				
			JSONObject obj = new JSONObject(dtoRsp.getResponseInfo());
			int status = Integer.parseInt(obj.getString("code"));
			String message = obj.getString("message");
			
			if(status == 1){
				// 上传成功！
				uploadrsp.setCode(0);				
			} else {
				// 上传失败！
				uploadrsp.setCode(1);
			}
			
			uploadrsp.setMessage(message);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			uploadrsp.setCode(1);
			uploadrsp.setMessage(e.toString());
			logger.error("UploadAction Upload Error: "+ e);	
		}
		
		return uploadrsp;
		// logger.info("UploadAction Upload Result: "+ uploadrsp.getCode());		
	}
	
	public static JSONArray getArray(){
		JSONArray jarr = new JSONArray();
		
		JSONObject job = new JSONObject();
		JSONObject descJson = new JSONObject();
	    try {
	    	descJson.put("uDes", "导航问题"
	    			+"导航起点:中国新疆维吾尔自治区昌吉回族自治州呼图壁县兵团芳草湖总场118乡道"
					+"导航终点:中国新疆维吾尔自治区五家渠市青湖路街道"
					+"起点坐标:44.53593187393898,86.68946026749464"
					+"终点坐标:44.17450126359637,87.54452265313402"
					+"所在城市:新疆维吾尔自治区"
					+"用户当前位置:44.53576578182362,86.68963065615563"
					+"图幅号：42F064021"
					+"图幅号：43F093014");
	    	/**
			descJson.put("uDes", "导航问题"
									+"导航起点:中国新疆维吾尔自治区昌吉回族自治州呼图壁县兵团芳草湖总场118乡道"
									+"导航终点:中国新疆维吾尔自治区五家渠市青湖路街道"
									+"起点坐标:44.53593187393898,86.68946026749464"
									+"终点坐标:44.17450126359637,87.54452265313402"
									+"所在城市:新疆维吾尔自治区"
									+"用户当前位置:44.53576578182362,86.68963065615563"
									+"图幅号：=42F064021"
									+"图幅号：=43F093014");
			**/
			String[] list = new String[2];
			list[0] = "http://192.168.1.94/road_Attachments/test.jpg";
			list[1] = "http://192.168.1.94/road_Attachments/XMGD_2014072802.jpeg";
			
			job.put("dataId","gaokuai-12701630");
			//job.put("cityCode","500000");
			job.put("cityName","新疆维吾尔自治区  昌吉回族自治州");
			//job.put("errorType","POI");
			job.put("subtype","车厂");							  //  大类型
			job.put("errorDesc","(子类型)目的地位置有误，请调查 ！");      		// 子类型
			job.put("title","Snowman_2015080551");
			job.put("description",descJson.toString());
			job.put("submitUser","car-company");
			job.put("userName","周兵");
			job.put("points","106.344768,29.361543|106.331720,29.293516");
			job.put("priority","S");
			//job.put("x","106.331688");
			//job.put("y","29.293595");
			job.put("div","15Q1");
			job.put("imageUrl",list);
			//job.put("imageUrl","http://p1.gexing.com/G1/M00/26/3D/rBACE1JMCI6QPZUyAABTlXPZZQM855.jpg");
			job.put("attachUrl","http://p1.gexing.com/G1/M00/26/3D/rBACE1JMCI6QPZUyAABTlXPZZQM855.jpg");
			//job.put("dip","");
			job.put("submitTime","2015-08-05 14:38:55");
			job.put("addTime","2015-07-13 12:21:03");
			job.put("promotion","0");
			//job.put("modelid","");	
			
			jarr.put(job);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jarr;
	}
	
	public static JSONArray getArray1(){
		
	JSONArray jarr = new JSONArray();
	
	JSONObject job = new JSONObject();
	try {
		job.put("rid",3513);			
		job.put("reportId","04");
		job.put("status","04");
		job.put("statusDesc","04");		
		
		jarr.put(job);
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return jarr;
}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		uploadInfo();
	}

}
