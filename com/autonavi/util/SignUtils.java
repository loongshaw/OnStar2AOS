package com.autonavi.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;

public class SignUtils {

	/**
	 * @param 获取签名
	 */
	static Logger logger = Logger.getLogger(SignUtils.class); //SignUtils为类名
	
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
		logger.info("SignUtils.getSignStr raw_string: "+ raw_string);
		sign_string = Md5Utils.Md5(raw_string);	
		
		// 转换大小写
		sign_string_uppercase = sign_string.toUpperCase();
			
		//return "D8251D7495E62FDC1D9D15A73D0234CC";
		return sign_string_uppercase;
	}
	
	public static String getSignStr1(String str){
		
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
		logger.info("SignUtils.getSignStr raw_string: "+ raw_string);
		sign_string = Md5Utils.md5Encode(raw_string);	
		
		// 转换大小写
		sign_string_uppercase = sign_string.toUpperCase();
			
		//return "D8251D7495E62FDC1D9D15A73D0234CC";
		return sign_string_uppercase;
	}
	
	public static String changeCharset(String str, String newCharset)
		   throws UnsupportedEncodingException {
		  if (str != null) {
		   //用默认字符编码解码字符串。
		   byte[] bs = str.getBytes();
		   //用新的字符编码生成字符串
		   return new String(bs, newCharset);
		  }
		  return null;
	 }
	
	// 测试用main方法
	public static void main(String[] args){
		
		JSONObject descJson = new JSONObject();
	    try {	    	
			descJson.put("uDes", "目的地附近500米范围内缺少道路");
		} catch (JSONException e) {
			
		}
	    
	    String dd = descJson.toString();
	    
	    String xx = "onstar5001"+dd+"@nsJgU67OJHsSdUITG8dHK18eyrKxbPdN";
	    
	    //String xx_utf_8 = "";
	    //try {
		//	xx_utf_8 = new String(xx.getBytes("UTF-8"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		String sign = getSignStr(xx);
		String sign1 = getSignStr1(xx);
		String sign_up = sign.toUpperCase();
		String sign_up1 = sign1.toUpperCase();
		System.out.println("签名码为："+xx);
		System.out.println("签名码大写为："+sign_up);
		System.out.println("签名码大写为："+sign_up1);
		
	}

}
