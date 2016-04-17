package com.github.hualuomoli.web.ret.api;

import com.github.hualuomoli.web.ret.MessageReturn;

/**
 * API return message
 * status use success
 * @author hualuomoli
 *
 */
public abstract class ApiMessageReturn implements MessageReturn {

	private static final long serialVersionUID = 2667643904151187508L;

	public static final String SUCCESS = "0";

	private String code;

	public ApiMessageReturn() {
	}

	public ApiMessageReturn(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
