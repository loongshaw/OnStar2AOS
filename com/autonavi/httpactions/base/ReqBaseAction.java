package com.autonavi.httpactions.base;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


import com.autonavi.constants.Constants;
import com.autonavi.dto.base.BaseDtoReq;
import com.autonavi.dto.base.BaseDtoRsp;
import com.autonavi.dto.rsp.BaseRspDto;
import com.autonavi.util.SignUtils;
import com.autonavi.util.StreamUtils;


@SuppressWarnings({"unused", "deprecation" })
public class ReqBaseAction <T extends BaseRspDto>{

	/**
	 * @param http请求基础类
	 */
	static Logger logger = Logger.getLogger(ReqBaseAction.class); //ReqBaseAction为类名
	// 请求地址
	// public final static String UpLoad_HOST = Constants.SERVER_ROOT_URL_UPLOAD; 
	// public final static String DownLoad_HOST = Constants.SERVER_ROOT_URL_DOWNLOAD; 	
	
	// 请求状态
	// public static int Status = 0; 	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDtoRsp<T>  doHttpForObject(String req,int status,String URL){
		BaseDtoRsp<T>  dtoRsp = new BaseDtoRsp();
		
		String json = "";
		String error = "";
		Exception exteption = null;
		
		try {
			json = doHttp(req,status,URL);				
			// System.out.println("json: " + json);
		} catch (ConnectException e1) {
			exteption = e1;
			error = "请检查网络是否正常";
		} catch(ConnectTimeoutException e1){
			exteption = e1;
			error = "连接超时异常网络异常";
		} catch(SocketTimeoutException e1){
			exteption = e1;
			error = "socket超时异常网络异常";
		} catch(UnknownHostException e1){
			exteption = e1;
			error = "dns网络异常";
		} catch (Exception e1) {
			exteption = e1;
			error = "未知错误.";
			logger.info("doHttpForObject: "+e1.toString());
		}
		
		if (exteption != null) {
			dtoRsp.setHasResponse(false);
			dtoRsp.setStatus(1);
			dtoRsp.setErrorMsg(error);
			logger.info("ReqBaseAction doHttpForObject: "+exteption.toString());
			return (BaseDtoRsp<T>) dtoRsp;
		} else{
			dtoRsp.setHasResponse(true);
			dtoRsp.setStatus(0);
			dtoRsp.setSuccess(true);
			dtoRsp.setHasException(false);			
		}
		
		dtoRsp.setResponseInfo(json);
		
		return dtoRsp;
	}
	
	protected String doHttp(String req,int status,String url) throws ConnectException, Exception{
		
		HttpPost post = null;

		post = new HttpPost(url);
		logger.info("ReqBaseAction.doHttp url:"+url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		StringBuffer cc = new StringBuffer();

		String[] pairs = req.split("&");
		for (int i = 0; i < pairs.length; i++) {
			String[] keyvalue = pairs[i].split("=");
			String value = "";
			if (keyvalue.length > 1) {
				value = keyvalue[1];
			}
			// Log.d("mytag", keyvalue[0] + "=" + value);
			if (i != 0) {
				cc.append("&" + keyvalue[0] + "=" + value);
				// cc.append("&" + keyvalue[0] + "=" + value);
			} else {
				cc.append(keyvalue[0] + "=" + value);
				// cc.append(keyvalue[0] + "=" + value);
			}
			nvps.add(new BasicNameValuePair(keyvalue[0], value));
		}			
		String ss = cc.toString();
		// logger.info("ReqBaseAction.doHttp 编码后的请求：" + ss+"字符串长度为:"+cc.toString().length());

		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constants.REQUEST_TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constants.REQUEST_TIMEOUT);
		post.setEntity(new UrlEncodedFormEntity(nvps, BaseDtoReq.ENCODING));
		HttpResponse response = null;
		response = client.execute(post);

		String data = "";
		// 响应数据
		HttpEntity httpEntity = response.getEntity();
		//String response1 = EntityUtils.toString( httpEntity,"GB18030");
		if(status == 1){
			String result1 = new String(EntityUtils.toString(httpEntity).getBytes("ISO_8859_1"),"UTF-8");
			String result = URLDecoder.decode(result1,"GB18030");
			// logger.info("response1: " + result);
			return result;
		}
		
		if (httpEntity != null) {
			InputStream inputStream = httpEntity.getContent();	
			data = StreamUtils.convertStreamToString(inputStream);					
				// logger.info("response串: " + data);
		}							
		return data;		
	}

}
