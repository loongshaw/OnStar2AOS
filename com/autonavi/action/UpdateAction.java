package com.autonavi.action;

import org.apache.log4j.Logger;

import com.autonavi.business.RepeatItemsDealWith;
import com.autonavi.constants.Constants;
import com.autonavi.util.DateUtils;

public class UpdateAction implements Runnable{

	/**
	 * @note 更新输出结果服务：应对重复问题匹配结果(定时每晚21:00)
	 */
	static Logger logger = Logger.getLogger(UploadAction.class); //UploadAction为类名
	
	public static void doAction(){
		
		logger.info("业务3:OnStar重复数据匹配反馈结果！");
		// 文件扫描+更新结果
		RepeatItemsDealWith.doBusiness();
	}

	public void run() {
		// TODO Auto-generated method stub		
		
		while(true){
			// 周末不执行
			String week = DateUtils.getWeek();
			
			if(week.equals("星期六") || week.equals("星期日")){
				;
			}else{
				
				if(Constants.MODE == 0){
					// 测试模式
					doAction();
					logger.info("[UpdateAction] --- 测试模式");
				}else if(Constants.MODE == 1){
					// 正式模式
					int hours = Integer.parseInt(DateUtils.getDate(0,2));
					
					if(hours == 21){
						// 执行动作
						doAction();
						logger.info("[UpdateAction] --- 正式模式,time is "+hours+",week "+week);
					}
				}						
			}			
			
			try{
				if(Constants.MODE == 0){
					// 测试模式
					/*休眠10分钟*/
					logger.info("[UpdateAction] --- 测试模式,week "+week);
					logger.info("[UpdateAction] --- sleep 10 minutes");	
					Thread.sleep(600000);						
				}else if(Constants.MODE == 1){
					// 正式模式
					/*休眠60分钟*/
					logger.info("[UpdateAction] --- 正式模式,week "+week);
					logger.info("[UpdateAction] --- sleep 60 minutes");	
					Thread.sleep(3600000);						
				}					
			}catch(InterruptedException e){  
				logger.error("UpdateAction-中断异常"+e);
				return;     
			}
		}
	}

}
