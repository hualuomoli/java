package com.github.hualuomoli.mvc.exception.entity;

import java.io.Serializable;

import com.github.hualuomoli.commons.json.JsonMapper;

/**
 * 异常处理
 * @author hualuomoli
 *
 */
public class ErrorData implements Serializable {

	private static final long serialVersionUID = 7853106897448509957L;

	public static final String CODE = "errorcode";
	public static final String MSG = "errormsg";

	private String errorCode;
	private String errorMsg;

	public ErrorData() {
	}

	public ErrorData(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String toJson() {
		return JsonMapper.toJsonString(this);
	}

}
