package com.autonavi.services;

import com.autonavi.util.DateUtils;
import com.autonavi.util.XmlUtils;

public class RecordIDService {

	/**
	 * @param 每天ID记录值
	 */
	public static int getNextRecordID(){
		
		int id = 0;
		String recordId = "";
		recordId = XmlUtils.readCountIdOfXml();
		
		if(!recordId.equals("")){
			id = Integer.parseInt(recordId) + 1;			
		}
		
		if(XmlUtils.updateCountIdOfXml(id+"")){
			return id;
		}
		
		return 0;
	}
	
	// 获取形如：001、002、010、088等编号
	public static String getFormatedRecordID(){
		
		String str = "";
		int id = getNextRecordID();
		
		if(id < 10){
			str = "00"+id;
		}else if(id >= 10 && id < 100){
			str = "0"+id;
		}else if(id >= 100){
			str = ""+id;
		}
		
		return str;
	}
	
	// 获取编号：OnStar_2015042012
	public static String getRecordNo(){
		
		String no = "";
		String date = DateUtils.getCurrentDate();
		String id = getFormatedRecordID();		
		no = "OnStar_"+date+id ;
		return no;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("NO: "+ getRecordNo());
	}

}
