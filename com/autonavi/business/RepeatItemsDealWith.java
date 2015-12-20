package com.autonavi.business;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.autonavi.constants.Constants;
import com.autonavi.services.ExcelService;
import com.autonavi.services.ReadOutputExcelService;
import com.autonavi.services.RefreshFileListService;

public class RepeatItemsDealWith {

	/**
	 * @note 业务：重复记录处理
	 * @note 说明：1、重复记录获取反馈结果
	 */
	static Logger logger = Logger.getLogger(RepeatItemsDealWith.class); //RepeatItemsDealWith为类名
	
	public static void doBusiness(){
		
		// 输出文件夹路径
		List<String> filelist = RefreshFileListService.getFileList(Constants.filePath_Xlsx_Output);
		
		for(int i = 0; i < filelist.size(); i++)  
        {  
			String filepath = filelist.get(i);
			
			executeUpdate(filepath,filelist);						
        }
		
	}
	
	// 执行更新动作：1、读取已有反馈数据，获得list列表；2、更新EXCEL指定行数据
	public static void executeUpdate(String filepath,List<String> filelist){
		
		//boolean result = false;
		
		long time_start = System.currentTimeMillis(); 
		File file = new File(filepath);
		String fileName = file.getName();
		// 获得查询的输出文档反馈信息
		List<HashMap<String,String>> listmap = ReadOutputExcelService.readExcel(file);		
		// 更新EXCEL
		/**
		for(int i = 0; i < filelist.size(); i++){
			String filepath_i = filelist.get(i);
			File file_i = new File(filepath_i);
			String fileName_i = file_i.getName();
			result = ExcelService.updateExcelTable(listmap,0,fileName_i,0);
		}
		**/
		boolean result = ExcelService.updateExcelTable(listmap,0,fileName,0);
		logger.info("RepeatItemsDealWith.executeUpload 更新输出执行结果:" + result);					
		long time_end = System.currentTimeMillis(); 
		logger.info("RepeatItemsDealWith.executeUpload----FileName:"+fileName +"----Spend time:"+ (time_end-time_start));	
		
	}
	
}
