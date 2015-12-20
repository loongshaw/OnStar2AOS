package com.autonavi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * @param 获取日期
	 */
	public static String getCurrentDate(){		
		String date = null;
		Date currentTime = new Date();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		date = formatter.format(currentTime);	    
		return date;  
	}
	
	public static String getCurrentDate2(){		
		String date = null;
		Date currentTime = new Date();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		date = formatter.format(currentTime);	    
		return date;  
	}
	
	public static String getDate(int day,int type){
		String stringDate = null;
		
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-day);
        Date currentTime=cal.getTime();			
		
		if(type == 1){
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			stringDate = formatter1.format(currentTime);
		}else if(type == 2){
			SimpleDateFormat formatter2 = new SimpleDateFormat("HH");
			stringDate = formatter2.format(currentTime);
		}else if(type == 3){
			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMddHHmmss");
			stringDate = formatter3.format(currentTime);
		}else if(type == 4){
			SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stringDate = formatter4.format(currentTime);
		}else if(type == 5){
			SimpleDateFormat formatter5 = new SimpleDateFormat("mm");
			stringDate = formatter5.format(currentTime);
		}else if(type == 6){
			SimpleDateFormat formatter6 = new SimpleDateFormat("yyyyMMdd");
			stringDate = formatter6.format(currentTime);
		}
		return stringDate;
	}
	
	public static String getWeek(){   
		Date date =  new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  
        String week = sdf.format(date);  
        return week;  
    }  
	
	public static void main(String[] args){
		System.out.println("date :" + getDate(5,1));
		System.out.println("week :" + getWeek());
	}

}
