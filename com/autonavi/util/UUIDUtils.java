package com.autonavi.util;

import java.util.UUID;

public class UUIDUtils {

	/**
	 * @param UUID Generator
	 */
	
	public static String getUUID() { 		
        UUID uuid = UUID.randomUUID();     
        String str = uuid.toString();                  
        return str.toUpperCase();     
    }     
	
	public static String getUUIDS() {     
        UUID uuid = UUID.randomUUID();     
        String str = uuid.toString();     
        // 去掉"-"符号     
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);     
        return str+","+temp;     
    }     
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("UUID为："+getUUID());
	}

}
