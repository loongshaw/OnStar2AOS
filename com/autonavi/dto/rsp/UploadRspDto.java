package com.autonavi.dto.rsp;



public class UploadRspDto extends BaseRspDto{

	/**
	 * @param AOS返回类
	 */
	
	// 返回结果:0成功，1失败
	int code;	
	String message;
	
	public void setCode(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
