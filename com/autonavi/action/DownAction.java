package com.autonavi.action;

import org.apache.log4j.Logger;

import com.autonavi.business.DownLoadDataToEXCEL;
import com.autonavi.constants.Constants;
import com.autonavi.util.DateUtils;

public class DownAction implements Runnable{

	/**
	 * @note 数据下载：周一至周五早八点和晚五点执行下载
	 */
	static Logger logger = Logger.getLogger(DownAction.class); //DownAction为类名
	
	public static void doAction(){
		
		logger.info("业务2:Adam数据下载！");
		// 文件扫描+文件上传
		DownLoadDataToEXCEL.doBusiness();
	}

	public void run() {
		// TODO Auto-generated method stub		
		
		while(true){
			// 周末不执行
			String week = DateUtils.getWeek();
			
			if(week.equals("星期六") || week.equals("星期日")){
				// 测试模式
				doAction();
				logger.info("[DownAction] --- 测试模式");
			}else{

				if(Constants.MODE == 0){
					// 测试模式
					doAction();
					logger.info("[DownAction] --- 测试模式");
				}else if(Constants.MODE == 1){
					// 正式模式
					int hours = Integer.parseInt(DateUtils.getDate(0,2));
					
					if(hours == 7 || hours == 10 || hours == 11 || hours == 16 || hours == 17){
						// 执行动作
						doAction();
						logger.info("[DownAction] --- 正式模式,time is "+hours+",week "+week);
					}
				}
			}			
															
			try{
				if(Constants.MODE == 0){
					// 测试模式
					/*休眠30分钟*/
					logger.info("[DownAction] --- sleep 30 minutes");	
					Thread.sleep(1800000);						
				}else if(Constants.MODE == 1){
					// 正式模式
					/*休眠60分钟*/
					logger.info("[DownAction] --- sleep 60 minutes");	
					Thread.sleep(3600000);						
				}
			}catch(InterruptedException e){  
				logger.error("DownAction-中断异常"+e);
				return;     
			}
		}
	}

}
