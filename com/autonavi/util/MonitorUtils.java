package com.autonavi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MonitorUtils {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = null;
		try {			
			// Process exec = Runtime.getRuntime().exec("ipconfig");
			Process exec = Runtime.getRuntime().exec("pgrep -f OnStar2AOS_20150617.jar");

			InputStream inputStream = exec.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			stringBuilder = new StringBuilder(10);
		  
			String tmp;
			while ((tmp = bufferedReader.readLine()) != null) {
				stringBuilder.append(tmp);
			}

		  	} catch (IOException e) {
		  		e.printStackTrace();
		  	} finally {
			  try {
				  bufferedReader.close();
			  } catch (IOException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  System.out.println(stringBuilder.toString());
	}

}
