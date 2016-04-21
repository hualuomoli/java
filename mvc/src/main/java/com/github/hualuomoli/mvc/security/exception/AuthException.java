package com.github.hualuomoli.mvc.security.exception;

import com.github.hualuomoli.mvc.exception.entity.ErrorData;

/**
 * 权限异常
 * @author hualuomoli
 *
 */
public class AuthException extends MvcException {

	private static final long serialVersionUID = 879054420696384909L;

	public AuthException(String code, String msg) {
		super(code, msg);
	}

	public AuthException(ErrorData errorData) {
		super(errorData);
	}

	public AuthException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public AuthException(ErrorData errorData, Throwable cause) {
		super(errorData, cause);
	}

}
