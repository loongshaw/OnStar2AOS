package com.autonavi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/** 
 * 流操作工具
 * @date 2014-4-22  
 */
public class StreamUtils {
	
	static Logger logger = Logger.getLogger(StreamUtils.class); //StreamUtils为类名

	public static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			logger.error("StreamUtils.convertStreamToString error: "+ e);     
			//e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("StreamUtils.convertStreamToString error: "+ e);     
				//e.printStackTrace();
			}
		}

		return sb.toString();
	}
}
