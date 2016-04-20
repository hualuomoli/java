package com.github.hualuomoli.mvc.security.exception;

import java.net.URLEncoder;

import com.github.hualuomoli.commons.constant.Charset;

/**
 * 权限异常
 * @author hualuomoli
 *
 */
public abstract class MvcException extends RuntimeException {

	private static final long serialVersionUID = -6936931609600847923L;

	public abstract int getStatus();

	private String errorCode;
	private String errorMsg;

	public MvcException(String message) {
		super(message);
		this.setErrorCode(String.valueOf(this.getStatus()));
		this.setErrorMsg();
	}

	public MvcException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.setErrorMsg();
	}

	public MvcException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.setErrorMsg();
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

	private void setErrorMsg() {
		try {
			errorMsg = URLEncoder.encode(this.getMessage(), Charset.UTF8.getEncoding());
		} catch (Exception e) {
		}

	}

}
