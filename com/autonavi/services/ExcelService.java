package com.autonavi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;
import com.autonavi.util.DateUtils;
import com.autonavi.util.FileUtils;

public class ExcelService {

	/**
	 * @param 用于创建EXCEL
	 */
	static Logger logger = Logger.getLogger(ExcelService.class); //ExcelService为类名
	
	public static boolean createExcelTable(String filePath){
		boolean result_return = false;
		
		//解析JSONArray数组
		//String key1 = "id";
		//String key2 = "add_time";
		//String key3 = "origin_lat";
		//String key4 = "origin_lon";
		//String key5 = "dest_lat";
		//String key6 = "dest_lon";
		//String key7 = "status_message";		
		//String key8 = "submitter";
		//String key9 = "result";
		String key1 = "ONSTAR_ID";
		String key2 = "ADAM_ID";		
		String key3 = "AOS_ID";
		//String key3 = "points";
		String key4 = "FID";							// FID
		String key5 = "PROCESSING_START_TIME";			// 时间
		
		String key6 = "ORIGIN_LAT";						// 纬度
		String key7 = "ORIGIN_LON";						// 经度
		String key8 = "DEST_LAT";
		String key9 = "DEST_LON";
		String key10 = "DESCRIPTION";
		
		String key11 = "DEST_STREET_NAME";
		String key12 = "DEST_CITY";
		String key13 = "DEST_STATE";	
		
		String key14 = "REPLY";
		String key15 = "REPLY_DATE";
		String key16 = "REPLY_COMMENT";
		String key17 = "REPEAT";
		
		
		String filepath = null;
		
		XSSFWorkbook wb = new XSSFWorkbook();//创建工作薄  
		Sheet sheet = wb.createSheet("Sheet0");//创建工作表，名称为test 
		//创建标题栏
		//创建行
		XSSFRow row_t = (XSSFRow) sheet.createRow(0);  
		//创建列
		XSSFCell cell0_t = row_t.createCell((short)0);  
		XSSFCell cell1_t = row_t.createCell((short)1);  
		XSSFCell cell2_t = row_t.createCell((short)2);  
		XSSFCell cell3_t = row_t.createCell((short)3);  
		XSSFCell cell4_t = row_t.createCell((short)4);  
		XSSFCell cell5_t = row_t.createCell((short)5);  
		XSSFCell cell6_t = row_t.createCell((short)6);  
		XSSFCell cell7_t = row_t.createCell((short)7);  
		XSSFCell cell8_t = row_t.createCell((short)8);  
		XSSFCell cell9_t = row_t.createCell((short)9); 
		XSSFCell cell10_t = row_t.createCell((short)10); 
		XSSFCell cell11_t = row_t.createCell((short)11);  
		XSSFCell cell12_t = row_t.createCell((short)12);  
		XSSFCell cell13_t = row_t.createCell((short)13);  
		XSSFCell cell14_t = row_t.createCell((short)14);  
		XSSFCell cell15_t = row_t.createCell((short)15); 	
		XSSFCell cell16_t = row_t.createCell((short)16); 	

		//设置列值
		cell0_t.setCellValue(key1);
		cell1_t.setCellValue(key2);
		cell2_t.setCellValue(key3);
		cell3_t.setCellValue(key4);
		cell4_t.setCellValue(key5);
		cell5_t.setCellValue(key6);
		cell6_t.setCellValue(key7);
		cell7_t.setCellValue(key8);
		cell8_t.setCellValue(key9);
		cell9_t.setCellValue(key10);
		cell10_t.setCellValue(key11);
		cell11_t.setCellValue(key12);
		cell12_t.setCellValue(key13);
		cell13_t.setCellValue(key14);
		cell14_t.setCellValue(key15);
		cell15_t.setCellValue(key16);
		cell16_t.setCellValue(key17);
								
		// 获取文件路径
		// filePath = "d://"+filename+"tongji.xls";
		filepath = filePath;
		//文件输出
		try {
			FileOutputStream out =  new FileOutputStream(filepath);
			// FileOutputStream out =  new FileOutputStream("d://"+filename+"tongji.xls");
			wb.write(out);
			out.close();
			
			result_return = true;			
			logger.info("ExcelService createExcelTable Result: "+ result_return);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			result_return = false;
			logger.error("ExcelService createExcelTable Error: "+ e);	
		}            		
		return result_return;
	}
	
	public static boolean writeExcelTable(JSONArray jsonArray,int day){
		boolean result_return = false;
		boolean weatherExistFile = false;
		
		FileInputStream inputFS = null;
		Sheet sheet = null;
		String filePath = null;
		File file = null;
					
		//解析JSONArray数组
		//String key1 = "aosid";
		//String key2 = "add_time";
		//String key3 = "origin_lat";
		//String key4 = "origin_lon";
		//String key5 = "dest_lat";
		//String key6 = "dest_lon";
		//String key7 = "status_message";		
		//String key8 = "submitter";
		//String key9 = "result";
		
		
		String key1 = "aosid";		
		String key2 = "title";
		String key3 = "points";
		
		String key4 = "description";
		String key5 = "reply";
		String key6 = "reply_date";
		String key7 = "reply_comment";	
		
		String key8 = "adamid";	
		
		String key9 = "fid";
		String key10 = "processing_start_time";
		String key11 = "dest_street_name";
		String key12 = "dest_city";
		String key13 = "dest_state";
		String key14 = "repeat";
		
		// 获取文件名
		// String filename = "Onstar-[Customer Problem Management]_"+DateUtils.getCurrentDate()+".xlsx";
		String filename = "Onstar-[Customer Problem Management]_"+DateUtils.getDate(day,6)+".xlsx";
		// 获取文件路径
		// filePath = "d://"+filename+"tongji.xls";
		filePath = Constants.filePath_Xlsx_Output+filename;		
		
		try {
			JSONArray jsonarray = jsonArray;
			int length = jsonarray.length();
			// 获取输出EXCEL文件夹所有文件路径
			List<String> filelist = RefreshFileListService.getFileList(Constants.filePath_Xlsx_Output);
			for(int i = 0; i < filelist.size(); i++)  
	        {  
				String filepath = filelist.get(i);
				
				File temp_file = new File(filepath.trim());
				String fileName = temp_file.getName();
												
				if(fileName.equals(filename) == true){
					weatherExistFile = true;
				}								
	        }
			
			if(weatherExistFile == false){
				createExcelTable(filePath);
				logger.info("ExcelService.writeExcelTable result: "+ "创建文件夹成功！"+ "--是否存在文件夹: "+weatherExistFile);
			}
			
			file = new File(filePath);
			inputFS = new FileInputStream(file);
			//读取工作薄  
			XSSFWorkbook wb = new XSSFWorkbook(inputFS);
			sheet = wb.getSheetAt(0); 
			int base_row = sheet.getPhysicalNumberOfRows();
						
			for(int i=0;i <length; i++){		
				
				//获取JSONObject
				JSONObject jsonObj = jsonarray.getJSONObject(i);
				
				String adamid = jsonObj.getString(key8);
				String aosid = jsonObj.getString(key1);
				String onstarid = jsonObj.getString(key2);	
				
				String points = jsonObj.getString(key3);
				String origin_lat = "";
				String origin_lon = "";
				String dest_lat = "";
				String dest_lon = "";		
				if(!points.equals("") && points != null){
					String[] str_arr = points.split("\\|");
					String[] origin = str_arr[0].split(",");
					String[] dest = str_arr[1].split(",");
					origin_lon = origin[0];
					origin_lat = origin[1];
					dest_lon = dest[0];
					dest_lat = dest[1];		
				}
								
				String description = jsonObj.getString(key4);
				String reply = jsonObj.getString(key5);	
				String reply_date = jsonObj.getString(key6);
				String reply_comment = jsonObj.getString(key7);	
				
				// 新增key获取
				String fid = jsonObj.getString(key9);
				String processing_start_time = jsonObj.getString(key10);
				String dest_street_name = jsonObj.getString(key11);
				String dest_city = jsonObj.getString(key12);
				String dest_state = jsonObj.getString(key13);	
				String repeat = jsonObj.getString(key14);	
				
				//创建行
				XSSFRow row = (XSSFRow) sheet.createRow(i+base_row);  
				//创建列
				XSSFCell cell0 = row.createCell((short)0);  
				XSSFCell cell1 = row.createCell((short)1);  
				XSSFCell cell2 = row.createCell((short)2);  
				XSSFCell cell3 = row.createCell((short)3);  
				XSSFCell cell4 = row.createCell((short)4);  
				XSSFCell cell5 = row.createCell((short)5); 
				XSSFCell cell6 = row.createCell((short)6);  
				XSSFCell cell7 = row.createCell((short)7);  
				XSSFCell cell8 = row.createCell((short)8);  
				XSSFCell cell9 = row.createCell((short)9);  
				XSSFCell cell10 = row.createCell((short)10); 
				XSSFCell cell11 = row.createCell((short)11);  
				XSSFCell cell12 = row.createCell((short)12);  
				XSSFCell cell13 = row.createCell((short)13);  
				XSSFCell cell14 = row.createCell((short)14);  
				XSSFCell cell15 = row.createCell((short)15); 
				XSSFCell cell16 = row.createCell((short)16); 
 
				//设置列值
				cell0.setCellValue(onstarid);
				/**
				if(adamid.equals("") || adamid == null){
					cell1.setCellValue("/");
				}else{
					cell1.setCellValue(adamid);
				}
				**/
				cell1.setCellValue(adamid);
				/**
				if(aosid.equals("") || aosid == null){
					cell2.setCellValue("/");
				}else{
					cell2.setCellValue(aosid);
				}
				**/
				cell2.setCellValue(aosid);
				
				cell3.setCellValue(fid);
				cell4.setCellValue(processing_start_time);
				
				cell5.setCellValue(origin_lat);
				cell6.setCellValue(origin_lon);
				cell7.setCellValue(dest_lat);
				cell8.setCellValue(dest_lon);
				cell9.setCellValue(description);
				
				cell10.setCellValue(dest_street_name);
				cell11.setCellValue(dest_city);
				cell12.setCellValue(dest_state);
				
				/**
				if(reply.equals("") || reply == null){
					cell8.setCellValue("/");
				}else{
					cell8.setCellValue(reply);
				}
				
				if(reply_date.equals("") || reply_date == null){
					cell9.setCellValue("/");
				}else{
					cell9.setCellValue(reply_date);
				}
				
				if(reply_comment.equals("") || reply_comment == null){
					cell10.setCellValue("/");
				}else{
					cell10.setCellValue(reply_comment);
				}
				**/
				
				cell13.setCellValue(reply);
				cell14.setCellValue(reply_date);
				cell15.setCellValue(reply_comment);
				
				cell16.setCellValue(repeat);
				
				//cell.setCellStyle(null);  
			} 					
			//文件输出
			FileOutputStream out =  new FileOutputStream(filePath);
			// FileOutputStream out =  new FileOutputStream("d://"+filename+"tongji.xls");			
			wb.write(out);			
			out.close();
			result_return = true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("ExcelService.writeExcelTable Error: 文件未找到！"+ e);
			result_return = false;
			return result_return;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("ExcelService.writeExcelTable Error: Json"+ e);
			result_return = false;
			return result_return;
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("ExcelService.writeExcelTable Error: "+ e);
			result_return = false;
			return result_return;
		}        			           		
		return result_return;
	}
	
	// state: 0 表示更新重复数据,1 表示更新下载数据
	@SuppressWarnings("rawtypes")
	public static boolean updateExcelTable(List<HashMap<String,String>> list_data,int day,String filename,int state){
		boolean result_return = false;
		
		FileInputStream inputFS = null;
		Sheet sheet = null;
		String fileName = null;
		String filePath = null;
		File file = null;
		
		XSSFRow row_xlsx = null; 
		// XSSFCell cel2 = null;
		// XSSFCell cel9 = null;
										
		String key1 = "title";		
		String key2 = "adamid";
		String key3 = "aosid";		
		
		String key4 = "origin_lat";
		String key5 = "origin_lon";
		String key6 = "dest_lat";
		String key7 = "dest_lon";	
		
		String key8 = "description";	
		String key9 = "reply";	
		String key10 = "reply_date";	
		String key11 = "reply_comment";	
		
		String key12 = "points";
		
		// 获取文件名
		if(state == 1){
			// 下载更新状态
			fileName = "Onstar-[Customer Problem Management]_"+DateUtils.getDate(day,6)+".xlsx";
		}else if(state == 0){
			// 重复更新状态
			fileName = filename;
		}		
		// 获取文件路径		
		filePath = Constants.filePath_Xlsx_Output+fileName;	
		//filePath = Constants.filePath_Xlsx_Output+filename;	
		
		if(FileUtils.fileExists(filePath) == false){
			logger.info("ExcelService.updateExcelTable 源文件不存在，不进行更新！ "+ fileName);
			return false;
		}else{
			// 源文件存在，则进行判断是否符合格式。
			boolean isRight = isExcelTableRight(filePath);			
			if(isRight == false){
				logger.info("ExcelService.updateExcelTable 源文件存在，但格式不匹配，不进行更新！ "+ fileName);
				return false;
			}
		}
		
		int count_need = 0;
		int count_result = 0;
		
		int done = 0;
		
		try {
			List<HashMap<String,String>> list = list_data;
			int length = list.size();
						
			file = new File(filePath);
			inputFS = new FileInputStream(file);
			// 读取工作薄  
			XSSFWorkbook wb = new XSSFWorkbook(inputFS);
			sheet = wb.getSheetAt(0); 
			int base_row = sheet.getPhysicalNumberOfRows();
			
			// 从第一行开始遍历
			for(int i=1;i <base_row; i++){
				// 初始化是否被更新!
				done = 0;
				// 获取每一行
				row_xlsx = (XSSFRow) sheet.getRow(i);				
				XSSFCell cel2 = row_xlsx.getCell((short)1); 
				XSSFCell cel14 = row_xlsx.getCell((short)13); 
				XSSFCell cel17 = row_xlsx.getCell((short)16); 
				
				// System.out.println("第"+i+"行--"+ cel2 +","+ cel9 );
				// 读取每一行的adam_ID 和  reply_date
				String adam_id = ReadOutputExcelService.getXSSFCellValue(cel2);
				String reply = ReadOutputExcelService.getXSSFCellValue(cel14);
				String repeat = ReadOutputExcelService.getXSSFCellValue(cel17);
				// 判断adam_id是否为空，为空则进行更新
				if(state == 1){
					
					if(adam_id.equals("/") && reply.equals("/") && repeat.equals("1")){
						// 需更新内容处理
						XSSFCell  cel6 = row_xlsx.getCell((short)5); 
		        		XSSFCell  cel7 = row_xlsx.getCell((short)6); 
		        		XSSFCell  cel8 = row_xlsx.getCell((short)7); 
		        		XSSFCell  cel9 = row_xlsx.getCell((short)8); 
		        		
		        		String origin_lat = ReadOutputExcelService.getXSSFCellValue(cel6);
		        		String origin_lon = ReadOutputExcelService.getXSSFCellValue(cel7);
		        		String dest_lat = ReadOutputExcelService.getXSSFCellValue(cel8);
		        		String dest_lon = ReadOutputExcelService.getXSSFCellValue(cel9);
		        		// 组装points，来自excel
		        		String points = origin_lat+","+origin_lon+"|"+dest_lat+","+dest_lon;
		        		// 数据对比：更新重复行数据
		        		for(int j=0;j <length; j++){			    				
		    				// 获取待对比的数据map,来自list
		    				HashMap<String,String> map = new HashMap<String,String>();
		    				
		    				for(Iterator it = list.get(j).keySet().iterator() ; it.hasNext();){
	    			            String key = it.next().toString();
	    			            map.put(key, list.get(j).get(key));
	    			        }
		    				
		    				String onstarid_li = map.get(key1);	
		    				String adamid_li = map.get(key2);
		    				String aosid_li = map.get(key3);
		    				
		    				String points_lii = map.get(key12);
		    				
		    				String origin_lat_li = "";
		    				String origin_lon_li = "";
		    				String dest_lat_li = "";
		    				String dest_lon_li = "";	
		    				
		    				if(!points_lii.equals("") && points_lii != null){
		    					String[] str_arr = points_lii.split("\\|");
		    					String[] origin = str_arr[0].split(",");
		    					String[] dest = str_arr[1].split(",");
		    					origin_lon_li = origin[0];
		    					origin_lat_li = origin[1];
		    					dest_lon_li = dest[0];
		    					dest_lat_li = dest[1];		
		    				}	    							
		    								
		    				String description_li = map.get(key8);
		    				String reply_li = map.get(key9);	
		    				String reply_date_li = map.get(key10);
		    				String reply_comment_li = map.get(key11);
		    				
		    				// 组装points
		    				String points_li = origin_lat_li+","+origin_lon_li+"|"+dest_lat_li+","+dest_lon_li;
		    				
		    				// 对比执行
		    				if(points_li.equals(points) == true){
		    					// 获取待更新的列
		    					XSSFCell  cell1 = row_xlsx.createCell((short)0); 
		    	        		XSSFCell  cell2 = row_xlsx.createCell((short)1); 
		    	        		XSSFCell  cell3 = row_xlsx.createCell((short)2); 
		    					
		    					XSSFCell  cell14 = row_xlsx.createCell((short)13); 
		    	        		XSSFCell  cell15 = row_xlsx.createCell((short)14); 
		    	        		XSSFCell  cell16 = row_xlsx.createCell((short)15); 
		    	        		
		    	        		cell1.setCellValue(onstarid_li);
		    	        		cell2.setCellValue(adamid_li);
		    	        		cell3.setCellValue(aosid_li);	
		    	        		cell14.setCellValue(reply_li);
		    					cell15.setCellValue(reply_date_li);
		    					cell16.setCellValue(reply_comment_li);	
		    					// 统计已经更新的数据
		    					count_result++;
		    					
		    					logger.info("ExcelService.updateExcelTable 更新下载数据信息: "+ fileName +" 更新数据列:"+i);
		    					logger.info("ExcelService.updateExcelTable 更新数据信息: "+ onstarid_li+ ","+adamid_li+","+aosid_li+","+points_li+","+description_li+","+reply_li+","+reply_date_li+","+reply_comment_li);
		    					
		    					// 清空hashmap
			    				map.clear();
			    				// 已经更新一行记录！跳入下一行。
			    				done = 1;
		    				}
		    				
		    				if(done == 1){
		    					// 若已更新一条记录，则跳出本次循环。
		    					// logger.info("ExcelService.updateExcelTable 退出内循环1");
		    					break;
		    				}	    					    					    				
		        		}
		        		// 统计需要更新的数据
		        		count_need++;					
					}
					
				}else{
					
					if(adam_id.equals("/") && reply.equals("/") && repeat.equals("0")){
						// 初始化是否被更新!
						done = 0;
						// 需更新内容处理
						XSSFCell  cel4 = row_xlsx.getCell((short)3); 
						XSSFCell  cel6 = row_xlsx.getCell((short)5); 
		        		XSSFCell  cel7 = row_xlsx.getCell((short)6); 
		        		XSSFCell  cel8 = row_xlsx.getCell((short)7); 
		        		XSSFCell  cel9 = row_xlsx.getCell((short)8); 
		        		
		        		String fid = ReadOutputExcelService.getXSSFCellValue(cel4);
		        		String origin_lat = ReadOutputExcelService.getXSSFCellValue(cel6);
		        		String origin_lon = ReadOutputExcelService.getXSSFCellValue(cel7);
		        		String dest_lat = ReadOutputExcelService.getXSSFCellValue(cel8);
		        		String dest_lon = ReadOutputExcelService.getXSSFCellValue(cel9);
		        		// 组装points，来自excel
		        		String points = origin_lat+","+origin_lon+"|"+dest_lat+","+dest_lon;
		        		
		        		// 输出待更新行信息
		        		logger.info("ExcelService.updateExcelTable 待更新行数据信息: "+ fileName +" 更新数据列:"+i+ " 数据fid为"+fid);
		        		
		        		// 数据对比：更新重复行数据
		        		for(int j=0;j <length; j++){			    				
		    				// 获取待对比的数据map,来自list
		    				HashMap<String,String> map = new HashMap<String,String>();
		    				
		    				for(Iterator it = list.get(j).keySet().iterator() ; it.hasNext();){
	    			            String key = it.next().toString();
	    			            map.put(key, list.get(j).get(key));
	    			        }	    				
		    				
		    				String onstarid_li = map.get(key1);	
		    				String adamid_li = map.get(key2);
		    				String aosid_li = map.get(key3);
		    												
		    				String origin_lat_li = map.get(key4);
		    				String origin_lon_li = map.get(key5);
		    				String dest_lat_li = map.get(key6);
		    				String dest_lon_li = map.get(key7);					
		    								
		    				String description_li = map.get(key8);
		    				String reply_li = map.get(key9);	
		    				String reply_date_li = map.get(key10);
		    				String reply_comment_li = map.get(key11);
		    				
		    				// 组装points
		    				String points_li = origin_lat_li+","+origin_lon_li+"|"+dest_lat_li+","+dest_lon_li;
		    				
		    				// 对比执行
		    				if(points_li.equals(points) == true){
		    					// 获取待更新的列	    			    					
		    					XSSFCell  cell14 = row_xlsx.createCell((short)13); 
		    	        		XSSFCell  cell15 = row_xlsx.createCell((short)14); 
		    	        		XSSFCell  cell16 = row_xlsx.createCell((short)15); 
		    	        			    	        		
		    	        		cell14.setCellValue(reply_li);
		    					cell15.setCellValue(reply_date_li);
		    					cell16.setCellValue(reply_comment_li);	
		    					// 统计已经更新的数据
		    					count_result++;
		    					
		    					logger.info("ExcelService.updateExcelTable 更新重复数据信息: "+ fileName +" 更新数据列:"+i);
		    					logger.info("ExcelService.updateExcelTable 更新数据信息: "+ onstarid_li+ ","+adamid_li+","+aosid_li+","+points_li+","+description_li+","+reply_li+","+reply_date_li+","+reply_comment_li);
		    					
		    					// 清空hashmap
			    				map.clear();
			    				// 已经更新一行记录！跳入下一行。
			    				done = 1;
		    				}
		    				
		    				if(done == 1){
		    					// 若已更新一条记录，则跳出本次循环。
		    					break;
		    				}	    		
		        		}
		        		// 统计需要更新的数据
		        		count_need++;					
					}
				}								
				// logger.info("ExcelService.updateExcelTable 进入内循环2");				
			}															
			//文件输出
			FileOutputStream out =  new FileOutputStream(filePath);
			// FileOutputStream out =  new FileOutputStream("d://"+filename+"tongji.xls");			
			wb.write(out);			
			out.close();
			result_return = true;
			
			logger.info("ExcelService.updateExcelTable 更新数据汇总信息----文件名:"+ fileName +" 待更新数据条数:"+count_need +" 已更新数据条数:"+count_result);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("ExcelService.updateExcelTable Error: 文件未找到！"+ e);
			result_return = false;
			return result_return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("ExcelService.updateExcelTable Error: "+ e);
			result_return = false;
			return result_return;
		}        			           		
		return result_return;
	}
	
	public static boolean isExcelTableRight(String filepath){
		boolean result_return = false;
		
		FileInputStream inputFS = null;
		Sheet sheet = null;
		XSSFRow row_xlsx = null; 
		String filePath = null;
		File file = null;
									
		String key1 = "ONSTAR_ID";		
		String key2 = "ADAM_ID";
		String key3 = "AOS_ID";
		
		String key4 = "FID";
		String key5 = "PROCESSING_START_TIME";
		String key6 = "ORIGIN_LAT";
		String key7 = "ORIGIN_LON";			
		String key8 = "DEST_LAT";			
		String key9 = "DEST_LON";
		
		String key10 = "DESCRIPTION";
		String key11 = "DEST_STREET_NAME";
		String key12 = "DEST_CITY";
		String key13 = "DEST_STATE";
		String key14 = "REPLY";
		String key15 = "REPLY_DATE";
		String key16 = "REPLY_COMMENT";
		String key17 = "REPEAT";
						
		filePath = filepath;		
		
		try {
			
			file = new File(filePath);
			inputFS = new FileInputStream(file);
			//读取工作薄  
			XSSFWorkbook wb = new XSSFWorkbook(inputFS);
			sheet = wb.getSheetAt(0); 
			// 获取第0行的title值
			row_xlsx = (XSSFRow) sheet.getRow(0);	
			
			XSSFCell cel1 = row_xlsx.getCell((short)0); 
			XSSFCell cel2 = row_xlsx.getCell((short)1); 
			XSSFCell cel3 = row_xlsx.getCell((short)2); 
			XSSFCell cel4 = row_xlsx.getCell((short)3); 
			XSSFCell cel5 = row_xlsx.getCell((short)4); 
			XSSFCell cel6 = row_xlsx.getCell((short)5); 
			XSSFCell cel7 = row_xlsx.getCell((short)6); 
			XSSFCell cel8 = row_xlsx.getCell((short)7); 
			XSSFCell cel9 = row_xlsx.getCell((short)8); 
			XSSFCell cel10 = row_xlsx.getCell((short)9); 
			XSSFCell cel11 = row_xlsx.getCell((short)10); 
			XSSFCell cel12 = row_xlsx.getCell((short)11); 
			XSSFCell cel13 = row_xlsx.getCell((short)12); 
			XSSFCell cel14 = row_xlsx.getCell((short)13); 
			XSSFCell cel15 = row_xlsx.getCell((short)14); 
			XSSFCell cel16 = row_xlsx.getCell((short)15); 
			XSSFCell cel17 = row_xlsx.getCell((short)16);
			
			String onstar_id = "";
    		String aosid = "";
    		String adamid = "";
    		String fid = "";	
    		String process_start_time = "";
    		String origin_lat = "";
    		String origin_lon = "";
    		String dest_lat = "";	
    		String dest_lon = "";
    		String description = "";
    		String dest_street_name = "";
    		String dest_city = "";	
    		String dest_state = "";
    		String reply = "";
    		String reply_date = "";
    		String reply_comment = "";	
    		String repeat = "";	
    		
			if(cel1 != null){
				onstar_id = ReadOutputExcelService.getXSSFCellValue(cel1);
			}
			
			if(cel2 != null){
				aosid = ReadOutputExcelService.getXSSFCellValue(cel2);
			}
			
			if(cel3 != null){
				adamid = ReadOutputExcelService.getXSSFCellValue(cel3);
			}
			
			if(cel4 != null){
				fid = ReadOutputExcelService.getXSSFCellValue(cel4);	
			}
			
			if(cel5 != null){
				process_start_time = ReadOutputExcelService.getXSSFCellValue(cel5);
			}
			
			if(cel6 != null){
				origin_lat = ReadOutputExcelService.getXSSFCellValue(cel6);
			}
			
			if(cel7 != null){
				origin_lon = ReadOutputExcelService.getXSSFCellValue(cel7);
			}
			
			if(cel8 != null){
				dest_lat = ReadOutputExcelService.getXSSFCellValue(cel8);	
			}
			
			if(cel9 != null){
				dest_lon = ReadOutputExcelService.getXSSFCellValue(cel9);
			}
			
			if(cel10 != null){
				description = ReadOutputExcelService.getXSSFCellValue(cel10);
			}
			
			if(cel11 != null){
				dest_street_name = ReadOutputExcelService.getXSSFCellValue(cel11);
			}
			
			if(cel12 != null){
				dest_city = ReadOutputExcelService.getXSSFCellValue(cel12);	
			}
			
			if(cel13 != null){
				dest_state = ReadOutputExcelService.getXSSFCellValue(cel13);
			}
			
			if(cel14 != null){
				reply = ReadOutputExcelService.getXSSFCellValue(cel14);
			}
			
			if(cel15 != null){
				reply_date = ReadOutputExcelService.getXSSFCellValue(cel15);
			}
			
			if(cel16 != null){
				reply_comment = ReadOutputExcelService.getXSSFCellValue(cel16);	
			}
			
			if(cel17 != null){
				repeat = ReadOutputExcelService.getXSSFCellValue(cel17);	
			}
			   		
    		if(onstar_id.equals(key1) && aosid.equals(key2) && adamid.equals(key3) && fid.equals(key4) &&
    		   process_start_time.equals(key5) && origin_lat.equals(key6) && origin_lon.equals(key7) && dest_lat.equals(key8) &&
    		   dest_lon.equals(key9) && description.equals(key10) && dest_street_name.equals(key11) && dest_city.equals(key12) &&
    		   dest_state.equals(key13) && reply.equals(key14) && reply_date.equals(key15) && reply_comment.equals(key16) && repeat.equals(key17))
    		{
    			result_return = true;
    		}
											
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("ExcelService.isExcelTableRight Error: 文件未找到！"+ e);
			result_return = false;
			return result_return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("ExcelService.isExcelTableRight Error: "+ e);
			result_return = false;
			return result_return;
		}        			           		
		return result_return;
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		
		
		File file = new File("d://CSVfile//test//Onstar-[Customer Problem Management]_20150630.xlsx");
		String fileName = file.getName();
		
		List<HashMap<String,String>> listmap = ReadOutputExcelService.readExcel(file);
		
		//updateExcelTable(listmap,fileName);
		
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		// createExcelTable();
//		String key1 = "id";
//		String key2 = "add_time";
//		String key3 = "origin_lat";
//		String key4 = "origin_lon";
//		String key5 = "dest_lat";
//		String key6 = "dest_lon";
//		String key7 = "status_message";		
//		String key8 = "submitter";
//		String key9 = "result";
//		
//		JSONArray jsonarray = new JSONArray();
//		
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put(key1,"1");
//			obj.put(key2,"1");
//			obj.put(key3,"1");
//			obj.put(key4,"1");
//			obj.put(key5,"1");
//			obj.put(key6,"1");
//			obj.put(key7,"1");
//			obj.put(key8,"1");
//			obj.put(key9,"1");			
//			
//		} catch (JSONException e) {
////			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		jsonarray.put(obj);
//		jsonarray.put(obj);
//		
//		writeExcelTable(jsonarray);
//	}

}
