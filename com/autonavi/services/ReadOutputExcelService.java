package com.autonavi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadOutputExcelService {

	/**
	 * @param args
	 */
	static Logger logger = Logger.getLogger(ReadOutputExcelService.class); //ReadOutputExcelService为类名
	
	static String fileEncoding = "";
	static String fileEncoding_temp = "";
	
	// 用于获取EXCEL表格中所有的问题记录
	public static List<HashMap<String,String>> readExcel(File file){
		
		String fileName = file.getName();  
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName  
                .substring(fileName.lastIndexOf(".") + 1);  
        if ("xls".equals(extension)) { 
            return readExcelOfXls_allIn(file);  
        } else if ("xlsx".equals(extension)) {
            return readExcelOfXlsx_allIn(file);  
        } else {  
            //throw new IOException("不支持的文件类型");  
            logger.info("ReadOutputExcelService.readExcel IOException: "+ "不支持的文件类型");
            return null;
        }
 
	}
	
	public static List<HashMap<String,String>> readExcelOfXlsx(File file){
		
		List<HashMap<String,String>> listmap= new ArrayList<HashMap<String,String>>();
		//FileInputStream inputFS = null;
		InputStream inputFS = null;
		Sheet sheet = null;
		
		XSSFRow row_xlsx = null;  
		// fileEncoding = SysCharsetUtils.getSystemFileCharset();
		fileEncoding_temp = "GB18030";
		fileEncoding = "UTF-8";
        
		try {			
			inputFS = new FileInputStream(file);			
		    // POIFSFileSystem poifs = new POIFSFileSystem(inputFS);
		    Workbook workbook = new XSSFWorkbook(inputFS);
	        sheet = workbook.getSheetAt(0);
	        
	        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  
	        	row_xlsx = (XSSFRow) sheet.getRow(i);
	        	
	        	if(row_xlsx == null){
	        		;
	        	}else{
	        		 
	        		XSSFCell  cel1 = row_xlsx.getCell((short)0); 
	        		XSSFCell  cel2 = row_xlsx.getCell((short)1); 
	        		XSSFCell  cel3 = row_xlsx.getCell((short)2); 
	        		XSSFCell  cel4 = row_xlsx.getCell((short)3); 
	        		XSSFCell  cel5 = row_xlsx.getCell((short)4); 
	        		XSSFCell  cel6 = row_xlsx.getCell((short)5); 
	        		XSSFCell  cel7 = row_xlsx.getCell((short)6); 
	        		XSSFCell  cel8 = row_xlsx.getCell((short)7); 
	        		XSSFCell  cel9 = row_xlsx.getCell((short)8); 
	        		XSSFCell  cel10 = row_xlsx.getCell((short)9); 
	        		XSSFCell  cel11 = row_xlsx.getCell((short)10); 
	        		        			        		
	        		String onstar_id = getXSSFCellValue(cel1);
	        		
	        		String adam_id = "";
	        		if(cel2 == null){
	        			;
	        		}else{
	        			adam_id = getXSSFCellValue(cel2);
	        		}
	        		
	        		String aos_id = "";
	        		if(cel3 == null){
	        			;
	        		}else{
	        			aos_id = getXSSFCellValue(cel3);
	        		}
	        		
	        		String origin_lat = getXSSFCellValue(cel4);
	        		String origin_lon = getXSSFCellValue(cel5);
	        		String dest_lat = getXSSFCellValue(cel6);
	        		String dest_lon = getXSSFCellValue(cel7);
	        		String description = new String(getXSSFCellValue(cel8).getBytes(fileEncoding), fileEncoding);
	        		
	        		String reply = "";
	        		if(cel9 == null){
	        			;
	        		}else{
	        			reply = new String(getXSSFCellValue(cel9).getBytes(fileEncoding), fileEncoding);
	        		}
	        		
	        		String reply_date = "";
	        		if(cel10 == null){
	        			;
	        		}else{
	        			reply_date = getXSSFCellValue(cel10);
	        		}
	        		
	        		String reply_comment = "";
	        		if(cel11 == null){
	        			;
	        		}else{
	        			reply_comment = new String(getXSSFCellValue(cel11).getBytes(fileEncoding), fileEncoding);	
	        		}	        			        		       		
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("onstarid",onstar_id);
	        		map.put("adamid",adam_id);
	        		map.put("aosid",aos_id);
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("description",description);
	        		map.put("reply",reply);
	        		map.put("reply_date",reply_date);
	        		map.put("reply_comment",reply_comment);
	        		
	        		if(adam_id.equals("/") == false){
	        			listmap.add(map);
	        		}
	        		
	        		// System.out.println(onstar_id+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + description);
	        	}
	        }
	        	        
		} catch (Exception e) {
			logger.error("ReadOutputExcelService.readExcelItems Exception: "+ e);
			return null;
	    }

		return listmap;
	}
	
	public static List<HashMap<String,String>> readExcelOfXls(File file){
		
		List<HashMap<String,String>> listmap= new ArrayList<HashMap<String,String>>();
		FileInputStream inputFS = null;
		Sheet sheet = null;
				
        HSSFRow row_xls = null;          
        
		try {
			inputFS = new FileInputStream(file);

		    // POIFSFileSystem poifs = new POIFSFileSystem(inputFS);
		    Workbook workbook = new HSSFWorkbook(inputFS);
		    sheet = workbook.getSheetAt(0);
		    
		    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  
	        	row_xls = (HSSFRow) sheet.getRow(i);
	        	
	        	if(row_xls == null){
	        		;
	        	}else{
	        		
	        		HSSFCell  cel1 = row_xls.getCell(0); 
	        		HSSFCell  cel2 = row_xls.getCell(1); 
	        		HSSFCell  cel3 = row_xls.getCell(2); 
	        		HSSFCell  cel4 = row_xls.getCell(3); 
	        		HSSFCell  cel5 = row_xls.getCell(4); 
	        		HSSFCell  cel6 = row_xls.getCell(5); 
	        		HSSFCell  cel7 = row_xls.getCell(6); 
	        		HSSFCell  cel8 = row_xls.getCell(7); 
	        		HSSFCell  cel9 = row_xls.getCell(8); 
	        		HSSFCell  cel10 = row_xls.getCell(9); 
	        		HSSFCell  cel11 = row_xls.getCell(10); 
	        		        			        		
	        		String onstar_id = getHSSFCellValue(cel1);
	        		String adam_id = getHSSFCellValue(cel2);
	        		String aos_id = getHSSFCellValue(cel3);
	        		String origin_lat = getHSSFCellValue(cel4);
	        		String origin_lon = getHSSFCellValue(cel5);
	        		String dest_lat = getHSSFCellValue(cel6);
	        		String dest_lon = getHSSFCellValue(cel7);
	        		String description = new String(getHSSFCellValue(cel8).getBytes(fileEncoding), fileEncoding);
	        		String reply = new String(getHSSFCellValue(cel9).getBytes(fileEncoding), fileEncoding);
	        		String reply_date = getHSSFCellValue(cel10);
	        		String reply_comment = new String(getHSSFCellValue(cel11).getBytes(fileEncoding), fileEncoding);	        		
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("onstar_id",onstar_id);
	        		map.put("adam_id",adam_id);
	        		map.put("aos_id",aos_id);
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("description",description);
	        		map.put("reply",reply);
	        		map.put("reply_date",reply_date);
	        		map.put("reply_comment",reply_comment);
	        		
	        		listmap.add(map);
	        			        			        		
	        		// System.out.println(i+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + status_message);
	        	}
	        }
		    
		} catch (Exception e) {
			logger.error("ReadOutputExcelService.readExcelItems Exception: "+ e);
			return null;
	    }

		return listmap;
	}
	
	public static List<HashMap<String,String>> readExcelOfXlsx_allIn(File file){
		
		List<HashMap<String,String>> listmap= new ArrayList<HashMap<String,String>>();
		//FileInputStream inputFS = null;
		InputStream inputFS = null;
		Sheet sheet = null;
		
		XSSFRow row_xlsx = null;  
		// fileEncoding = SysCharsetUtils.getSystemFileCharset();
		fileEncoding_temp = "GB18030";
		fileEncoding = "UTF-8";
        
		try {			
			inputFS = new FileInputStream(file);			
		    // POIFSFileSystem poifs = new POIFSFileSystem(inputFS);
		    Workbook workbook = new XSSFWorkbook(inputFS);
	        sheet = workbook.getSheetAt(0);
	        
	        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  
	        	row_xlsx = (XSSFRow) sheet.getRow(i);
	        	
	        	if(row_xlsx == null){
	        		;
	        	}else{
	        		 
	        		XSSFCell  cel1 = row_xlsx.getCell((short)0); 
	        		XSSFCell  cel2 = row_xlsx.getCell((short)1); 
	        		XSSFCell  cel3 = row_xlsx.getCell((short)2); 
	        		XSSFCell  cel4 = row_xlsx.getCell((short)3); 
	        		XSSFCell  cel5 = row_xlsx.getCell((short)4); 
	        		XSSFCell  cel6 = row_xlsx.getCell((short)5); 
	        		XSSFCell  cel7 = row_xlsx.getCell((short)6); 
	        		XSSFCell  cel8 = row_xlsx.getCell((short)7); 
	        		XSSFCell  cel9 = row_xlsx.getCell((short)8); 
	        		XSSFCell  cel10 = row_xlsx.getCell((short)9); 
	        		XSSFCell  cel11 = row_xlsx.getCell((short)10); 
	        		XSSFCell  cel12 = row_xlsx.getCell((short)11); 
	        		XSSFCell  cel13 = row_xlsx.getCell((short)12); 
	        		XSSFCell  cel14 = row_xlsx.getCell((short)13); 
	        		XSSFCell  cel15 = row_xlsx.getCell((short)14); 
	        		XSSFCell  cel16 = row_xlsx.getCell((short)15); 
	        		XSSFCell  cel17 = row_xlsx.getCell((short)16); 
	        		        			        		
	        		String onstar_id = getXSSFCellValue(cel1);
	        		
	        		String adam_id = "";
	        		if(cel2 == null){
	        			;
	        		}else{
	        			adam_id = getXSSFCellValue(cel2);
	        		}
	        		
	        		String aos_id = "";
	        		if(cel3 == null){
	        			;
	        		}else{
	        			aos_id = getXSSFCellValue(cel3);
	        		}
	        		
	        		String fid = "";
	        		if(cel4 == null){
	        			;
	        		}else{
	        			fid = getXSSFCellValue(cel4);
	        		}
	        		
	        		String process_start_time = "";
	        		if(cel5 == null){
	        			;
	        		}else{
	        			process_start_time = getXSSFCellValue(cel5);
	        		}
	        		
	        		String origin_lat = getXSSFCellValue(cel6);
	        		String origin_lon = getXSSFCellValue(cel7);
	        		String dest_lat = getXSSFCellValue(cel8);
	        		String dest_lon = getXSSFCellValue(cel9);
	        		String description = new String(getXSSFCellValue(cel10).getBytes(fileEncoding), fileEncoding);
	        		
	        		String dest_street_name = "";
	        		if(cel11 == null){
	        			;
	        		}else{
	        			dest_street_name = getXSSFCellValue(cel11);
	        		}
	        		
	        		String dest_city = "";
	        		if(cel12 == null){
	        			;
	        		}else{
	        			dest_city = getXSSFCellValue(cel12);
	        		}
	        		
	        		String dest_state = "";
	        		if(cel13 == null){
	        			;
	        		}else{
	        			dest_state = getXSSFCellValue(cel13);
	        		}
	        		
	        		String reply = "";
	        		if(cel14 == null){
	        			;
	        		}else{
	        			reply = new String(getXSSFCellValue(cel14).getBytes(fileEncoding), fileEncoding);
	        		}
	        		
	        		String reply_date = "";
	        		if(cel15 == null){
	        			;
	        		}else{
	        			reply_date = getXSSFCellValue(cel15);
	        		}
	        		
	        		String reply_comment = "";
	        		if(cel16 == null){
	        			;
	        		}else{
	        			reply_comment = new String(getXSSFCellValue(cel16).getBytes(fileEncoding), fileEncoding);	
	        		}	        
	        		
	        		String repeat = "";
	        		if(cel17 == null){
	        			;
	        		}else{
	        			repeat = new String(getXSSFCellValue(cel17));	
	        		}	        
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("onstarid",onstar_id);
	        		map.put("adamid",adam_id);
	        		map.put("aosid",aos_id);
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("description",description);
	        		map.put("reply",reply);
	        		map.put("reply_date",reply_date);
	        		map.put("reply_comment",reply_comment);
	        		
	        		// 新增字段20150717
	        		map.put("fid",fid);
	        		map.put("process_start_time",process_start_time);
	        		map.put("dest_street_name",dest_street_name);
	        		map.put("dest_city",dest_city);
	        		map.put("dest_state",dest_state);
	        		map.put("repeat",repeat);
	        		
	        		if(repeat.equals("1") == true && adam_id.equals("/") == false){
	        			listmap.add(map);
	        		}	        		
	        		// System.out.println(onstar_id+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + description);
	        	}
	        }
	        	        
		} catch (Exception e) {
			logger.error("ReadOutputExcelService.readExcelItems Exception: "+ e);
			return null;
	    }

		return listmap;
	}
	
	public static List<HashMap<String,String>> readExcelOfXls_allIn(File file){
		
		List<HashMap<String,String>> listmap= new ArrayList<HashMap<String,String>>();
		FileInputStream inputFS = null;
		Sheet sheet = null;
				
        HSSFRow row_xls = null;          
        
		try {
			inputFS = new FileInputStream(file);

		    // POIFSFileSystem poifs = new POIFSFileSystem(inputFS);
		    Workbook workbook = new HSSFWorkbook(inputFS);
		    sheet = workbook.getSheetAt(0);
		    
		    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {  
	        	row_xls = (HSSFRow) sheet.getRow(i);
	        	
	        	if(row_xls == null){
	        		;
	        	}else{
	        		
	        		HSSFCell  cel1 = row_xls.getCell(0); 
	        		HSSFCell  cel2 = row_xls.getCell(1); 
	        		HSSFCell  cel3 = row_xls.getCell(2); 
	        		HSSFCell  cel4 = row_xls.getCell(3); 
	        		HSSFCell  cel5 = row_xls.getCell(4); 
	        		HSSFCell  cel6 = row_xls.getCell(5); 
	        		HSSFCell  cel7 = row_xls.getCell(6); 
	        		HSSFCell  cel8 = row_xls.getCell(7); 
	        		HSSFCell  cel9 = row_xls.getCell(8); 
	        		HSSFCell  cel10 = row_xls.getCell(9); 
	        		HSSFCell  cel11 = row_xls.getCell(10); 
	        		HSSFCell  cel12 = row_xls.getCell(11); 
	        		HSSFCell  cel13 = row_xls.getCell(12); 
	        		HSSFCell  cel14 = row_xls.getCell(13); 
	        		HSSFCell  cel15 = row_xls.getCell(14); 
	        		HSSFCell  cel16 = row_xls.getCell(15); 
	        		HSSFCell  cel17 = row_xls.getCell(16); 
	        		        			        		
	        		String onstar_id = getHSSFCellValue(cel1);
	        		String adam_id = getHSSFCellValue(cel2);
	        		String aos_id = getHSSFCellValue(cel3);
	        		
	        		String fid = getHSSFCellValue(cel4);
	        		String process_start_time = getHSSFCellValue(cel5);
	        		
	        		String origin_lat = getHSSFCellValue(cel6);
	        		String origin_lon = getHSSFCellValue(cel7);
	        		String dest_lat = getHSSFCellValue(cel8);
	        		String dest_lon = getHSSFCellValue(cel9);
	        		String description = new String(getHSSFCellValue(cel10).getBytes(fileEncoding), fileEncoding);
	        		
	        		String dest_street_name = getHSSFCellValue(cel11);
	        		String dest_city = getHSSFCellValue(cel12);
	        		String dest_state = getHSSFCellValue(cel13);	        		
	        		
	        		String reply = new String(getHSSFCellValue(cel14).getBytes(fileEncoding), fileEncoding);
	        		String reply_date = getHSSFCellValue(cel15);
	        		String reply_comment = new String(getHSSFCellValue(cel16).getBytes(fileEncoding), fileEncoding);	
	        		
	        		String repeat = getHSSFCellValue(cel17);	 
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("onstar_id",onstar_id);
	        		map.put("adam_id",adam_id);
	        		map.put("aos_id",aos_id);
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("description",description);
	        		map.put("reply",reply);
	        		map.put("reply_date",reply_date);
	        		map.put("reply_comment",reply_comment);
	        		
	        		// 新增字段20150717
	        		map.put("fid",fid);
	        		map.put("process_start_time",process_start_time);
	        		map.put("dest_street_name",dest_street_name);
	        		map.put("dest_city",dest_city);
	        		map.put("dest_state",dest_state);
	        		map.put("repeat",repeat);
	        		
	        		if(repeat.equals("1") == true && adam_id.equals("/") == false){
	        			listmap.add(map);
	        		}	        			        			        		
	        		// System.out.println(i+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + status_message);
	        	}
	        }
		    
		} catch (Exception e) {
			logger.error("ReadOutputExcelService.readExcelItems Exception: "+ e);
			return null;
	    }

		return listmap;
	}
	
	public static String getHSSFCellValue(HSSFCell  cel){
		String value = "";
		
		switch (cel.getCellType()) {  
        	case HSSFCell.CELL_TYPE_STRING:  
        		value = cel.getStringCellValue();  
        		break;  
        	case HSSFCell.CELL_TYPE_NUMERIC:  
        		value = cel.getNumericCellValue()+"";
        		break;  
        	case HSSFCell.CELL_TYPE_BOOLEAN:  
        		value = cel.getBooleanCellValue()+"";  
        		break;  
        	case HSSFCell.CELL_TYPE_BLANK:  	           
	            value = "";  
	            break;  
        	default:  	            
	            value = cel.toString();  
        	}  
		return value;
	}
	
	public static String getXSSFCellValue(XSSFCell  cel){
		String value = "";
		
		switch (cel.getCellType()) {  
        	case XSSFCell.CELL_TYPE_STRING:  
        		value = cel.getStringCellValue();  
        		break;  
        	case XSSFCell.CELL_TYPE_NUMERIC:  
        		value = cel.getNumericCellValue()+"";
        		break;  
        	case XSSFCell.CELL_TYPE_BOOLEAN:  
        		value = cel.getBooleanCellValue()+"";  
        		break;  
        	case XSSFCell.CELL_TYPE_BLANK:  	           
	            value = "";  
	            break;  
        	default:  	            
	            value = cel.toString();  
        	}  
		return value;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("D://OnStar_2015042003.xlsx");
		
		readExcel(file);
	}

}
