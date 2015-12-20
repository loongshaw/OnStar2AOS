package com.autonavi.util;

import com.autonavi.constants.Constants;

public class PathUtils {

	/**
	 * @param 获取文件相对路径
	 */
	public String getPath(String filename){
		String path = "";		
		path = this.getClass().getClassLoader().getResource("com/autonavi/config/"+filename).getPath();		
		return path;
	}
	
	public String getAbsolutePath(){
		String path = "";		
		path = Constants.filePath_Config;	
		return path;
	}
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("path: "+ new PathUtils().getPath("Records.xml"));
	}

}
