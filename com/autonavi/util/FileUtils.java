package com.autonavi.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.autonavi.constants.Constants;

public class FileUtils {

	/**
	 * @note 文件创建操作
	 */
	static Logger logger = Logger.getLogger(FileUtils.class); //FileUtils为类名
	
	public static boolean createFile(String filepath){
		boolean result = false;
		//String filePath = Constants.filePath;
		//String fileName = Constants.fileName;
		
		//File file=new File(filePath+fileName);
		File file=new File(filepath);
	    if(file.exists())
	    {	       
	        result=true;
	    } else {		    	
	        try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("FileUtils.createFile error: "+ "文件路径" + filepath);
				logger.error("FileUtils.createFile error: "+ "文件创建失败！" + e);
				// e.printStackTrace();
			}
	        result=true;
	        // System.out.println("文件已经被成功创建");
	        }		
		return result;
	}	
	
	public static boolean fileExists(String filepath){
		boolean result = false;
		
		File file=new File(filepath);
	    if(file.exists())
	    {	       
	        result=true;
	        logger.info("FileUtils.fileExists info: "+ "文件已存在！");
	    }
	        
		return result;
	}
	
	public static String getOutPutFilePath(int day){
		String result = "";
		
		String filename = "Onstar-[Customer Problem Management]_"+DateUtils.getDate(day,6)+".xlsx";
		// 获取文件路径	
		result = Constants.filePath_Xlsx_Output+filename;		
	        
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// createFile();
	}

}
