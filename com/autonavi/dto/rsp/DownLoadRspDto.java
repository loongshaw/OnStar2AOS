package com.autonavi.dto.rsp;

import java.sql.Date;

public class DownLoadRspDto extends BaseRspDto{

	/**
	 * @param Adam返回类
	 */
	
	String adamid;							// ADAMID
	int aosid;							// AOSID
	Date add_date;						// 提交时间
	int status;							// 状态
	String description;					// 问题描述信息
	String title;						// 主题
	String points;						// 坐标点集合
	String submitter;					// 提交者
	String reply;						// 回复者
	String reply_date;					// 回复时间
	String reply_comment;				// 处理结果
		
	public void setAdamID(String adamid){
		this.adamid = adamid;
	}
		
	public String getAdamID(){
		return adamid;
	}
	
	public void setAOSID(int aosid){
		this.aosid = aosid;
	}
		
	public int getAOSID(){
		return aosid;
	}
	
	public void setDate(Date add_date){
		this.add_date = add_date;
	}
		
	public Date getDate(){
		return add_date;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
		
	public int getStatus(){
		return status;
	}
		
	public void setDescription(String description){
		this.description = description;
	}
		
	public String getDescription(){
		return description;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
		
	public String getTitle(){
		return title;
	}
	
	public void setPoints(String points){
		this.points = points;
	}
		
	public String getPoints(){
		return points;
	}
	
	public void setSubmitter(String submitter){
		this.submitter = submitter;
	}
		
	public String getSubmitter(){
		return submitter;
	}
	
	public void setReply(String reply){
		this.reply = reply;
	}
		
	public String getReply(){
		return reply;
	}
	
	public void setReplyDate(String reply_date){
		this.reply_date = reply_date;
	}
		
	public String getReplyDate(){
		return reply_date;
	}
	
	public void setReplyComment(String reply_comment){
		this.reply_comment = reply_comment;
	}
		
	public String getReplyComment(){
		return reply_comment;
	}

}
