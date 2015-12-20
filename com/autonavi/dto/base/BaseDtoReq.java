package com.autonavi.dto.base;

import com.autonavi.constants.Constants;


public class BaseDtoReq {

	/** 
	 * 基础 request的dto
	 * @date 2015-05-25
	 */
	public static final String ENCODING = "UTF-8";

	public String propsToUrlString() {
		return "";
	}

	@Override
	public String toString() {
		return propsToUrlString();
	}

	public String propsToUrl() {
		StringBuffer sb = new StringBuffer();
		if (Constants.CHANNEL != null) {
			sb.append("channel=" + Constants.CHANNEL);
		}
		
		String str = propsToUrlString();
		if (str != null && str.length() != 0) {
			sb.append("&" + str);
		}

		return sb.toString();
	}


}
