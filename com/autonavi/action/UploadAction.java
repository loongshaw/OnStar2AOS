package com.autonavi.action;

import org.apache.log4j.Logger;

import com.autonavi.business.UploadDataFromEXCEL;
import com.autonavi.constants.Constants;
import com.autonavi.util.DateUtils;

public class UploadAction implements Runnable{
	
	/**
	 * @note 数据上传：周一至周五执行上传
	 */
	static Logger logger = Logger.getLogger(UploadAction.class); //UploadAction为类名
	
	public static void doAction(){
		
		logger.info("业务1:OnStar数据上传AOS！");
		// 文件扫描+文件上传
		UploadDataFromEXCEL.doBusiness();
	}

	public void run() {
		// TODO Auto-generated method stub		
		
		while(true){
			// 周末不执行
			String week = DateUtils.getWeek();
			
			if(week.equals("星期六") || week.equals("星期日")){
				;
			}else{
				// 执行动作
				doAction();								
			}			
			
			try{
				if(Constants.MODE == 0){
					// 测试模式
					/*休眠10分钟*/
					logger.info("[UploadAction] --- 测试模式,week "+week);
					logger.info("[UploadAction] --- sleep 10 minutes");	
					Thread.sleep(600000);						
				}else if(Constants.MODE == 1){
					// 正式模式
					/*休眠60分钟*/
					logger.info("[UploadAction] --- 正式模式,week "+week);
					logger.info("[UploadAction] --- sleep 60 minutes");	
					Thread.sleep(3600000);						
				}					
			}catch(InterruptedException e){  
				logger.error("UploadAction-中断异常"+e);
				return;     
			}
		}
	}

}
