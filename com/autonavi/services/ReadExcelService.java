package com.autonavi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.autonavi.util.SysCharsetUtils;


@SuppressWarnings("unused")
public class ReadExcelService {

	/**
	 * @param EXCEL文件读取：1期实现整体功能，2期进行排查处理
	 */
	// private static int state = 0;  // 0表示xls,1表示xlsx
	
	static Logger logger = Logger.getLogger(ReadExcelService.class); //ReadExcelService为类名
	
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
            logger.info("ReadExcelService.readExcel IOException: "+ "不支持的文件类型");
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
	        		 
	        		XSSFCell  cel1 = row_xlsx.getCell((short)2); 
	        		XSSFCell  cel2 = row_xlsx.getCell((short)3); 
	        		XSSFCell  cel3 = row_xlsx.getCell((short)4); 
	        		XSSFCell  cel4 = row_xlsx.getCell((short)5); 
	        		XSSFCell  cel5 = row_xlsx.getCell((short)6); 
	        		
	        		//new String(getXSSFCellValue(cel1).getBytes(), fileEncoding);
	        		
	        		//String origin_lat = getXSSFCellValue(cel1);
	        		//String origin_lon = getXSSFCellValue(cel2);
	        		//String dest_lat = getXSSFCellValue(cel3);
	        		//String dest_lon = getXSSFCellValue(cel4);
	        		//String status_message = getXSSFCellValue(cel5);
	        		
	        		String origin_lat = new String(getXSSFCellValue(cel1));
	        		String origin_lon = new String(getXSSFCellValue(cel2));
	        		String dest_lat = new String(getXSSFCellValue(cel3));
	        		String dest_lon = new String(getXSSFCellValue(cel4));
	        		//String status_message_temp = new String(getXSSFCellValue(cel5).getBytes(), fileEncoding_temp);
	        		//String status_message = new String(getXSSFCellValue(cel5).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
	        		String status_message = new String(getXSSFCellValue(cel5).getBytes(fileEncoding), fileEncoding);	        		
	        		//String status_message = new String(status_message_temp.getBytes("ISO_8859_1"), "ISO_8859_1");
	        		//logger.info("ReadExcelService.readExcelOfXlsx status_message_temp: "+ status_message_temp);
	        		//String status_message = new String(status_message_temp.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
	        		//String status_message = SysCharsetUtils.toUTF_8(status_message_temp);	        		
	        		//logger.info("ReadExcelService.readExcelOfXlsx status_message: "+ status_message);
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("status_message",status_message);
	        		
	        		listmap.add(map);
	        		
	        		// System.out.println(i+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + status_message);
	        	}
	        }
	        	        
		} catch (Exception e) {
			logger.error("ReadExcelService.readExcelItems Exception: "+ e);
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
	        		
	        		HSSFCell  cel1 = row_xls.getCell(2); 
	        		HSSFCell  cel2 = row_xls.getCell(3); 
	        		HSSFCell  cel3 = row_xls.getCell(4); 
	        		HSSFCell  cel4 = row_xls.getCell(5); 
	        		HSSFCell  cel5 = row_xls.getCell(7); 
	        		
	        		String origin_lat = getHSSFCellValue(cel1);
	        		String origin_lon = getHSSFCellValue(cel2);
	        		String dest_lat = getHSSFCellValue(cel3);
	        		String dest_lon = getHSSFCellValue(cel4);
	        		String status_message = getHSSFCellValue(cel5);
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("status_message",status_message);
	        		
	        		listmap.add(map);
	        		
	        		// System.out.println(i+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + status_message);
	        	}
	        }
		    
		} catch (Exception e) {
			logger.error("ReadExcelService.readExcelItems Exception: "+ e);
			return null;
	    }

		return listmap;
	}
	
	// 2015年7月15日 调整方法,读取输入文档所有内容
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
	        int rows = sheet.getPhysicalNumberOfRows();
	        
	        for (int i = 1; i < rows; i++) {  
	        	row_xlsx = (XSSFRow) sheet.getRow(i);
	        	
	        	if(row_xlsx == null){
	        		;
	        	}else{
	        		 
	        		XSSFCell  cel1 = row_xlsx.getCell((short)0); 			// fid
	        		XSSFCell  cel2 = row_xlsx.getCell((short)1); 			// PROCESSING_START_TIME
	        		XSSFCell  cel3 = row_xlsx.getCell((short)2); 			// ORIGIN_LAT
	        		XSSFCell  cel4 = row_xlsx.getCell((short)3); 			// ORIGIN_LON
	        		XSSFCell  cel5 = row_xlsx.getCell((short)4); 			// DEST_LAT
	        		XSSFCell  cel6 = row_xlsx.getCell((short)5); 			// DEST_LON
	        		XSSFCell  cel7 = row_xlsx.getCell((short)6); 			// STATUS_MESSAGE
	        		XSSFCell  cel8 = row_xlsx.getCell((short)7); 			// DEST_STREET_NAME
	        		XSSFCell  cel9 = row_xlsx.getCell((short)8); 			// DEST_CITY
	        		XSSFCell  cel10 = row_xlsx.getCell((short)9); 			// DEST_STATE
	        		
	        		String fid = new String(getXSSFCellValue(cel1));
	        		String processing_start_time = new String(getXSSFCellValue(cel2));
	        		
	        		String origin_lat = new String(getXSSFCellValue(cel3));
	        		String origin_lon = new String(getXSSFCellValue(cel4));
	        		String dest_lat = new String(getXSSFCellValue(cel5));
	        		String dest_lon = new String(getXSSFCellValue(cel6));	        		
	        		String status_message = new String(getXSSFCellValue(cel7).getBytes(fileEncoding), fileEncoding);	
	        		
	        		String dest_street_name = "";
	        		String dest_city = "";
	        		String dest_state = "";
	        		
	        		if( cel8 == null ){
	        			;
	        		}else{
	        			dest_street_name = new String(getXSSFCellValue(cel8));
	        		}
	        		
	        		if( cel9 == null ){
	        			;
	        		}else{
	        			dest_city = new String(getXSSFCellValue(cel9));
	        		}
	        		
	        		if( cel10 == null ){
	        			;
	        		}else{
	        			dest_state = new String(getXSSFCellValue(cel10));
	        		}
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("fid",fid);
	        		map.put("processing_start_time",processing_start_time);
	        		
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("status_message",status_message);
	        		
	        		map.put("dest_street_name",dest_street_name);
	        		map.put("dest_city",dest_city);
	        		map.put("dest_state",dest_state);
	        		
	        		listmap.add(map);
	        		
	        		logger.info("ReadExcelService.readExcelItems list(待上传数据): "+rows+"-"+ i+"--"+ fid +"-" +processing_start_time+"-"+origin_lat +"-"+ origin_lon +"-"+ dest_lat +"-"+ dest_lon +"-" + status_message+"-"+ dest_street_name+"-" + dest_city +"-"+ dest_state);
	        		// System.out.println(i+"--"+ fid +"-" +processing_start_time+"-"+origin_lat +"-"+ origin_lon +"-"+ dest_lat +"-"+ dest_lon +"-" + status_message+"-"+ dest_street_name+"-" + dest_city +"-"+ dest_state);
	        	}
	        }
	        	        
		} catch (Exception e) {
			logger.error("ReadExcelService.readExcelItems Exception: "+ e);
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
	        			        		
	        		HSSFCell  cel1 = row_xls.getCell(0); 			// fid
	        		HSSFCell  cel2 = row_xls.getCell(1); 			// PROCESSING_START_TIME
	        		HSSFCell  cel3 = row_xls.getCell(2); 			// ORIGIN_LAT
	        		HSSFCell  cel4 = row_xls.getCell(3); 			// ORIGIN_LON
	        		HSSFCell  cel5 = row_xls.getCell(4); 			// DEST_LAT
	        		HSSFCell  cel6 = row_xls.getCell(5); 			// DEST_LON
	        		HSSFCell  cel7 = row_xls.getCell(6); 			// STATUS_MESSAGE
	        		HSSFCell  cel8 = row_xls.getCell(7); 			// DEST_STREET_NAME
	        		HSSFCell  cel9 = row_xls.getCell(8); 			// DEST_CITY
	        		HSSFCell  cel10 = row_xls.getCell(9); 			// DEST_STATE
	        		
	        		String fid = new String(getHSSFCellValue(cel1));
	        		String processing_start_time = new String(getHSSFCellValue(cel2));
	        		
	        		String origin_lat = getHSSFCellValue(cel3);
	        		String origin_lon = getHSSFCellValue(cel4);
	        		String dest_lat = getHSSFCellValue(cel5);
	        		String dest_lon = getHSSFCellValue(cel6);
	        		String status_message = getHSSFCellValue(cel7);
	        		
	        		String dest_street_name = new String(getHSSFCellValue(cel8));
	        		String dest_city = new String(getHSSFCellValue(cel9));
	        		String dest_state = new String(getHSSFCellValue(cel10));
	        		
	        		HashMap<String,String> map = new HashMap<String,String>();
	        		map.put("fid",fid);
	        		map.put("processing_start_time",processing_start_time);
	        		
	        		map.put("origin_lat",origin_lat);
	        		map.put("origin_lon",origin_lon);
	        		map.put("dest_lat",dest_lat);
	        		map.put("dest_lon",dest_lon);
	        		map.put("status_message",status_message);
	        		
	        		map.put("dest_street_name",dest_street_name);
	        		map.put("dest_city",dest_city);
	        		map.put("dest_state",dest_state);
	        		
	        		listmap.add(map);
	        		
	        		// System.out.println(i+"--"+origin_lat +" "+ origin_lon +" "+ dest_lat +" "+ dest_lon +" " + status_message);
	        	}
	        }
		    
		} catch (Exception e) {
			logger.error("ReadExcelService.readExcelItems Exception: "+ e);
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
		File file = new File("D://OnStar_2015101903.xlsx");
		
		readExcelOfXlsx_allIn(file);
	}

}
