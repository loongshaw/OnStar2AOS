package com.autonavi.entrance;

import org.apache.log4j.Logger;

import com.autonavi.action.DownAction;
import com.autonavi.action.UpdateAction;
import com.autonavi.action.UploadAction;


public class Entrance {

	/**
	 * @note 程序执行入口。
	 */
	static Logger logger = Logger.getLogger(Entrance.class); //Entrance为类名
	
	public static void main(String[] args) {
		
		logger.info("program start!");		
		// 业务1:OnStar数据上传至AOS
		UploadAction item1 = new UploadAction();		
		Thread robot1 = new Thread(item1);				
		robot1.start();		
		
		
		// 业务2:Adam数据下载！
		DownAction item2 = new DownAction();		
		Thread robot2 = new Thread(item2);				
		robot2.start();		
		
		// 业务3:输出数据更新！
		UpdateAction item3 = new UpdateAction();		
		Thread robot3 = new Thread(item3);				
		robot3.start();		
		
	}

}
