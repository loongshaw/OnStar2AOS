package com.autonavi.dto.base;

import java.util.List;

import com.autonavi.dto.rsp.BaseRspDto;

/** 
 * 基础 response的dto
 * @date 2015-05-25
 */

public class BaseDtoRsp <T extends BaseRspDto>{
	
	protected boolean success = true;  				//success表示有返回值
	protected boolean hasResponse;

	protected int status = 1 ; 						//默认表示失败,0表示成功呢
	
	protected String errorMsg; 						//错误内容
	private boolean hasException = false;			//
	
	private  String responseInfo;					//数据
														
	protected List<T> rspDtos;
	protected T rspDto;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isHasException() {
		return hasException || errorMsg!=null;
	}

	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}

	public List<T> getRspDtos() {
		return rspDtos;
	}

	public void setRspDtos(List<T> rspDtos) {
		this.rspDtos = rspDtos;
	}

	public T getRspDto() {
		return rspDto;
	}

	public void setRspDto(T rspDto) {
		this.rspDto = rspDto;
	}
	

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}
	public boolean isHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	@Override
	public String toString() {
		return "BaseDtoRsp [success=" + success + ", hasResponse="
				+ hasResponse + ", status=" + status + ", errorMsg=" + errorMsg
				+ ", hasException=" + hasException + ", responseInfo="
				+ responseInfo + ", rspDtos=" + rspDtos + ", rspDto=" + rspDto
				+ "]";
	}
	
}
