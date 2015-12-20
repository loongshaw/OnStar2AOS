package com.autonavi.util;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.autonavi.services.ReSchedulingService;

public class XmlUtils {

	/**
	 * @param 读取XML和更新XML
	 * @path 文件路径为：bin/com/autonavi/config/Records.xml
	 */
	static Logger logger = Logger.getLogger(XmlUtils.class); //XmlUtils为类名
	
	public static String readCountIdOfXml(){
		
		String id = "";
		String date = "";
		String currentdate = DateUtils.getCurrentDate();
		Document doc = readXml();
				
		NodeList accountList = doc.getElementsByTagName("account");  
		//logger.info("共有" + accountList.getLength() + "个account节点");          
	            
	    for(int i=0; i<accountList.getLength(); i++){
	        Element element = (Element)accountList.item(i);	                
	        //String nodename = element.getNodeName();	                	        
	        date = element.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
	        
	        if(date.equals(currentdate) == true){
	        	id = element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();						
			}else{
				id = "0";
			}
	    }
		
		return id;
	}
	
	public static boolean updateCountIdOfXml(String id){
		
		boolean result = false;
		String currentdate = DateUtils.getCurrentDate();
		
		try {  
			Document doc = readXml();
				
			NodeList accountList = doc.getElementsByTagName("account");  
			//logger.info("共有" + accountList.getLength() + "个account节点");          
	            
			for(int i=0; i<accountList.getLength(); i++){
				Element element = (Element)accountList.item(i);	                
				//String nodename = element.getNodeName();
				//System.out.println("nodename: "+ nodename);	        
				//element.getElementsByTagName("id").item(0).setTextContent(id);					
				String date = element.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();	
				
				if(date.equals(currentdate) == true){
					element.getElementsByTagName("id").item(0).setTextContent(id);					
				}else{
					String his_id = element.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
					String his_date = element.getElementsByTagName("date").item(0).getFirstChild().getNodeValue();	
					
					element.getElementsByTagName("id").item(0).setTextContent("1");	
					element.getElementsByTagName("date").item(0).setTextContent(currentdate);	
					
					// 将昨天的传递记录总数写入到txt文档中。
					if(ReSchedulingService.writeDownRecordTxt(his_id,his_date) == true){
						logger.info("XmlUtils.updateCountIdOfXml history record saved: "+ his_date+","+his_id);
					}
				}
			}
			// 数据保存
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source=new DOMSource(doc);
			
			//FileOutputStream outstream =new FileOutputStream(new File(new PathUtils().getPath("Records.xml")));			
			FileOutputStream outstream =new FileOutputStream(new File(new PathUtils().getAbsolutePath()));
			
			StreamResult reslut=new StreamResult(outstream);
			transformer.transform(source, reslut);
			outstream.close();	
			
			result = true;
			
		}catch (Exception e) { 
			logger.error("updateCountIdOfXml error: "+ e); 
			result = false; 
			return result;			    
		}  	
		return result;
	}
	
	public static Document readXml(){
		
		Document doc =null;
		// 解析xml文档内容
		try {  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			// doc = builder.parse(new PathUtils().getPath("Records.xml")); 							// 获取到xml文件	
			doc = builder.parse(new PathUtils().getAbsolutePath()); 									// 获取到xml文件	
		} catch (Exception e) {  
			logger.error("readXml error: "+ e);   
			return null;
		}  		
		return doc;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("id: "+ updateCountIdOfXml("233"));
	}

}
