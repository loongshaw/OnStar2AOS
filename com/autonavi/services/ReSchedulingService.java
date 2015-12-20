package com.autonavi.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.autonavi.constants.Constants;

public class ReSchedulingService {

	/**
	 * @note 排重服务:1、txt文档读写操作；2、对比操作。
	 * @note 逻辑：1、读txt,2、一行一行对比,3、返回结果,4、待post成功后执行txt写操作
	 */
	static Logger logger = Logger.getLogger(ReSchedulingService.class); //ReSchedulingService为类名
	
	// 数据比较
	@SuppressWarnings("unused")
	public static boolean compareWith(HashMap<String,String> map_temp){
				 
		 long time_start = System.currentTimeMillis(); 
		 boolean result = false;
		 InputStreamReader reader = null;
		 BufferedReader bufferedReader = null;
		 int num = 0;		 
         String encoding="UTF-8";
         // 默认输出的txt文件地址
         String txtFilePath = Constants.filePath_Txt;        
         // 待处理数据
 		 String origin_lat = "";
 		 String origin_lon = "";
 		 String dest_lat = "";
 		 String dest_lon = "";
 		 String status_message = "";
 		 // 传递的1个参数
 		 String points = "";
 		 
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
 		 
 		 points = origin_lon+","+origin_lat+"|"+dest_lon+","+dest_lat;
         
		 try 
		 {			 
            File file=new File(txtFilePath);
            if(file.isFile() && file.exists())
            { 	
           	 	//判断文件是否存在
                reader = new InputStreamReader(new FileInputStream(file),encoding);
                //考虑到编码格式
                bufferedReader = new BufferedReader(reader);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
               	 	num++;
                    
               	 	if(points.equals(lineTxt) == true){
               	 		result = true;
               	 	}
               	 	
               	 	if(result == true){
               	 		break;
               	 	}
               	 	// System.out.println(num+"--"+lineTxt);                  	 	              	 	
                }  
                long time_end = System.currentTimeMillis(); 
                logger.info("ReSchedulingService.compareWith Compare Counts: "+num +" Compared time:"+ (time_end-time_start));
             }else{
           	  	 // System.out.println("找不到指定的文件");
            	 // boolean createResult = createFile(file);
            	 logger.info("ReSchedulingService.compareWith Result: "+"找不到指定的文件");
            	 // logger.info("ReSchedulingService.compareWith Result: "+"文件创建"+createResult);
             }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			    result = false;
			  	logger.error("ReSchedulingService.compareWith Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
			  	result = false;
				logger.error("ReSchedulingService.compareWith Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(reader != null){
							reader.close();
						}
				  	
						if(bufferedReader != null){
							bufferedReader.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  result = false;
					  logger.error("ReSchedulingService.compareWith Result: "+"文件关闭异常" +e);
				  }
		  }
				 
		return result;
	}
		
	// 数据读取
	public static void readTxt(String filePath){
			
		 InputStreamReader reader = null;
		 BufferedReader bufferedReader = null;
		 int num = 0;		 
         String encoding="UTF-8";
		 try 
		 {			 
             File file=new File(filePath);
             if(file.isFile() && file.exists())
             { 	
            	 //判断文件是否存在
                 reader = new InputStreamReader(new FileInputStream(file),encoding);
                 //考虑到编码格式
                 bufferedReader = new BufferedReader(reader);
                 String lineTxt = null;
                 while((lineTxt = bufferedReader.readLine()) != null){
                	 num++;
                     System.out.println(num+"--"+lineTxt);
                     
                 }              
              }else{
            	  System.out.println("找不到指定的文件");
              }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			  	logger.error("ReSchedulingService.readTxt Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
				logger.error("ReSchedulingService.readTxt Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(reader != null){
							reader.close();
						}
				  	
						if(bufferedReader != null){
							bufferedReader.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  logger.error("ReSchedulingService.readTxt Result: "+"文件关闭异常" +e);
				  }
		  }
	}
	
	// 数据写入
	@SuppressWarnings("unused")
	public static boolean writeTxt(HashMap<String,String> map_temp){
		 boolean result = false;
		 OutputStreamWriter writer = null;
		 BufferedWriter bufferedWriter = null;
	 
         String encoding="UTF-8";
         // 默认输出的txt文件地址
         String txtFilePath = Constants.filePath_Txt;   
         // 待处理数据
 		 String origin_lat = "";
 		 String origin_lon = "";
 		 String dest_lat = "";
 		 String dest_lon = "";
 		 String status_message = "";
 		 // 传递的1个参数
 		 String points = "";
 		 
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
 		 
 		 points = origin_lon+","+origin_lat+"|"+dest_lon+","+dest_lat;
 		 
		 try 
		 {			 
            File file=new File(txtFilePath);
            if(file.isFile() && file.exists())
            { 	
           	 	//判断文件是否存在
            	writer = new OutputStreamWriter(new FileOutputStream(file,true),encoding);
                //考虑到编码格式
            	bufferedWriter = new BufferedWriter(writer);
               
                bufferedWriter.newLine();
                bufferedWriter.append(points);
                // 全部写入缓存中的内容
                bufferedWriter.flush(); 
                
                result = true;
             }else{
            	 // System.out.println("找不到指定的文件");
            	 logger.info("ReSchedulingService.writeTxt Result: "+"找不到指定的文件");
             }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			  	result = false;
			  	logger.error("ReSchedulingService.writeTxt Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
			  	result = false;
				logger.error("ReSchedulingService.writeTxt Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(writer != null){
							writer.close();
						}
				  	
						if(bufferedWriter != null){
							bufferedWriter.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  result = false;
					  logger.error("ReSchedulingService.writeTxt Result: "+"文件关闭异常" +e);
				  }
		  }
		 
		 return result;
	}
	
	// 数据比较-下载数据阶段
	public static boolean compareWithTXT(int aos_id){
		 
		 long time_start = System.currentTimeMillis(); 
		 boolean result = false;
		 InputStreamReader reader = null;
		 BufferedReader bufferedReader = null;
		 int num = 0;		 
         String encoding="UTF-8";
         // 默认输出的txt文件地址
         String txtFilePath = Constants.filePath_Txt_AOS;        
		 
		 String AOS_ID = aos_id+"";
				        
		 try 
		 {			 
           File file=new File(txtFilePath);
           if(file.isFile() && file.exists())
           { 	
          	   //判断文件是否存在
               reader = new InputStreamReader(new FileInputStream(file),encoding);
               //考虑到编码格式
               bufferedReader = new BufferedReader(reader);
               String lineTxt = null;
               while((lineTxt = bufferedReader.readLine()) != null){
              	 	num++;
                   
              	 	if(AOS_ID.equals(lineTxt) == true){
              	 		result = true;
              	 	}
              	 	
              	 	if(result == true){
              	 		break;
              	 	}
              	 	// System.out.println(num+"--"+lineTxt);                  	 	              	 	
               }  
               long time_end = System.currentTimeMillis(); 
               logger.info("ReSchedulingService.compareWithTXT Compare Counts: "+num +" Compared time:"+ (time_end-time_start));
            }else{
          	  	// System.out.println("找不到指定的文件");
            	// boolean createResult = createFile(file);           	 
           	 	logger.info("ReSchedulingService.compareWithTXT Result: "+"找不到指定的文件");           	 	
            }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			    result = false;
			  	logger.error("ReSchedulingService.compareWithTXT Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
			  	result = false;
				logger.error("ReSchedulingService.compareWithTXT Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(reader != null){
							reader.close();
						}
				  	
						if(bufferedReader != null){
							bufferedReader.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  result = false;
					  logger.error("ReSchedulingService.compareWithTXT Result: "+"文件关闭异常" +e);
				  }
		  }
		
		if(result == false){
			logger.info("ReSchedulingService.compareWithTXT Compare result: 没有重复，可以添加");		
		}else if(result == true){
			logger.info("ReSchedulingService.compareWithTXT Compare result: 已有重复记录，无需添加");		
		}
				
		return result;
	}
	// 数据写入-下载数据阶段
	public static boolean writeDownTxt(int aos_id){
		 boolean result = false;
		 OutputStreamWriter writer = null;
		 BufferedWriter bufferedWriter = null;
	 
         String encoding="UTF-8";
         // 默认输出的txt文件地址
         String txtFilePath = Constants.filePath_Txt_AOS;   
         // 待处理数据
 		 String AOS_ID = aos_id+"";
 		  		 
		 try 
		 {			 
            File file=new File(txtFilePath);
            if(file.isFile() && file.exists())
            { 	
           	 	//判断文件是否存在
            	writer = new OutputStreamWriter(new FileOutputStream(file,true),encoding);
                //考虑到编码格式
            	bufferedWriter = new BufferedWriter(writer);
               
                bufferedWriter.newLine();
                bufferedWriter.append(AOS_ID);
                // 全部写入缓存中的内容
                bufferedWriter.flush(); 
                
                result = true;
             }else{
            	 // System.out.println("找不到指定的文件");
            	 logger.info("ReSchedulingService.writeDownTxt Result: "+"找不到指定的文件");
             }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			  	result = false;
			  	logger.error("ReSchedulingService.writeDownTxt Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
			  	result = false;
				logger.error("ReSchedulingService.writeDownTxt Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(writer != null){
							writer.close();
						}
				  	
						if(bufferedWriter != null){
							bufferedWriter.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  result = false;
					  logger.error("ReSchedulingService.writeDownTxt Result: "+"文件关闭异常" +e);
				  }
		  }
		 
		 return result;
	}
	
	// 数据写入-下载数据阶段
	public static boolean writeDownRecordTxt(String counts,String date){
		 boolean result = false;
		 OutputStreamWriter writer = null;
		 BufferedWriter bufferedWriter = null;
	 
         String encoding="UTF-8";
         // 默认输出的txt文件地址
         String txtFilePath = Constants.filePath_Txt_CountRecord;   
         // 待处理数据
 		 String str = date+","+counts;
 		  		 
		 try 
		 {			 
            File file=new File(txtFilePath);
            if(file.isFile() && file.exists())
            { 	
           	 	//判断文件是否存在
            	writer = new OutputStreamWriter(new FileOutputStream(file,true),encoding);
                //考虑到编码格式
            	bufferedWriter = new BufferedWriter(writer);
               
                bufferedWriter.newLine();
                bufferedWriter.append(str);
                // 全部写入缓存中的内容
                bufferedWriter.flush(); 
                
                result = true;
             }else{
            	 // System.out.println("找不到指定的文件");
            	 logger.info("ReSchedulingService.writeDownRecordTxt Result: "+"找不到指定的文件");
             }
		  } catch (FileNotFoundException e) {
			  	// System.out.println("读取文件内容出错");
			  	// e.printStackTrace();
			  	result = false;
			  	logger.error("ReSchedulingService.writeDownRecordTxt Result: "+"文件未找到" +e);	
		  } catch (IOException e) {
				// e.printStackTrace();
			  	result = false;
				logger.error("ReSchedulingService.writeDownRecordTxt Result: "+"读取文件内容出错" +e);	
		  } finally {
			  try {
						if(writer != null){
							writer.close();
						}
				  	
						if(bufferedWriter != null){
							bufferedWriter.close();
						}					
					
				  } catch (IOException e) 
				  {
					  // e.printStackTrace();
					  result = false;
					  logger.error("ReSchedulingService.writeDownRecordTxt Result: "+"文件关闭异常" +e);
				  }
		  }
		 
		 return result;
	}
	
//	public static boolean createFile(File fileName){  
//		 boolean flag=false;  
//		 try
//		 {  
//		    if(!fileName.exists()){  
//		        fileName.createNewFile();  
//		        flag=true;  
//		    }  
//		 }catch(Exception e){  
//			 // e.printStackTrace(); 
//			 logger.error("ReSchedulingService.createFile Result: "+"文件创建异常" +e);
//		 }  
//		 return flag;  
//	}   
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String filePath = "D:\\dbf_record2.txt";
//		
//		boolean bb = compareWith("145678");
//		System.out.println("比较结果："+ bb);
//		
//		if(bb == false){
//			writeTxt(Constants.filePath_Txt,"145678");
//		}
//		writeTxt(filePath,"1235");
//		readTxt(Constants.filePath_Txt);
//	}

}
