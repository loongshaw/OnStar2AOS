package com.autonavi.action;

import org.apache.log4j.Logger;

public class ProtectionAction implements Runnable{

	/**
	 * @note 通过获取文件锁，来识别该程序是否运行
	 */
	static Logger logger = Logger.getLogger(ProtectionAction.class); //ProtectionAction为类名
	
	public static void doAction(){
		
		logger.info("业务3:守护线程！");
		
	}

	public void run() {
		// TODO Auto-generated method stub		
		
		while(true){
			
			// 执行动作
			doAction();
			
			try{
				/*休眠10分钟*/
				logger.info("sleep 10 minutes");
				Thread.sleep(600000);					
			}catch(InterruptedException e){  
				logger.error("ProtectionAction-中断异常"+e);
				return;     
			}
		}
	}


}
