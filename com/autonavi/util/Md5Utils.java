package com.autonavi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;



public class Md5Utils {

	/**
	 * @param 获取字符串的MD5编码
	 */
	static Logger logger = Logger.getLogger(Md5Utils.class); //Md5Utils为类名
	
	public static String Md5(String plainText) { 
		String result = null;
		try { 			
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes("UTF-8")); 
			byte b[] = md.digest(); 
			
			int i; 

			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) 
					i+= 256; 
				if(i<16) 
					buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			} 
			
			result = buf.toString();

			//System.out.println("result: " + result);//32位的加密 
			//System.out.println("result: " + result.substring(8,24));//16位的加密 

		} catch (NoSuchAlgorithmException e)
		{ 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			// 表明MD5编码异常
			result = null;
			
			logger.error("Md5编码异常——"+e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			// 表明MD5编码异常
			result = null;
			
			logger.error("Md5编码异常——"+e);
		}
		return result; 
	} 
	
	public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = null;
		try {
			byteArray = inStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
	
	// 返回大写的MD5编码
	public static String getSignStr(String str){
			
		String raw_string = "";
		String sign_string = "";
		String sign_string_uppercase = "";
		// 获取需要的参数列表
		String channel = Constants.KEY_NEEDS[0];							// 通道
		String type = Constants.KEY_NEEDS[1];								// 类型
		String decription = str;											// 问题描述
				
		// 拼接参数
		raw_string = channel + type + decription + Constants.ATKEY;
		//logger.info("SignUtils.getSignStr raw_string: "+ raw_string);
		//raw_string = "onstar" + "" + "" + Constants.ATKEY;
		// MD5编码	
		// logger.info("SignUtils.getSignStr raw_string: "+ raw_string);
		sign_string = Md5Utils.Md5(raw_string);	
			
		// 转换大小写
		sign_string_uppercase = sign_string.toUpperCase();
				
		//return "D8251D7495E62FDC1D9D15A73D0234CC";
		return sign_string_uppercase;
	}
	
	// 返回大写的MD5编码
	public static String getSignStr(){
				
		String raw_string = "";
		String sign_string = "";
		String sign_string_uppercase = "";							
		// 拼接参数
		raw_string = "OnStar@Ga1dEOrAL2ocV8";		
		// MD5编码	
		sign_string = Md5Utils.Md5(raw_string);					
		// 转换大小写
		sign_string_uppercase = sign_string.toUpperCase();
					
		return sign_string_uppercase;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject descJson = new JSONObject();
	    try {	    	
			descJson.put("uDes", "目的地附近500米范围内缺少道路");
		} catch (JSONException e) {
			
		}
	    
	    String dd = descJson.toString();
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append("onstar5001");
	    sb.append("{\"uDes\":\"目的地附近500米范围内缺少道路\"}");
	    sb.append("@nsJgU67OJHsSdUITG8dHK18eyrKxbPdN");	    
	    
	    String xx = "onstar5001"+dd+"@nsJgU67OJHsSdUITG8dHK18eyrKxbPdN";
	    // String xx1 = "OnStar@nsJgU67OJHsSdUITG8dHK18eyrKxbPdN";
	    
		System.out.println("签名码大写为："+Md5(xx));
		System.out.println("签名码大写为："+getSignStr(descJson.toString()));
		System.out.println("签名码大写为："+getSignStr());
	}

}
