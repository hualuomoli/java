package com.github.hualuomoli.mvc.security.exception;

import com.github.hualuomoli.mvc.exception.entity.ErrorData;

/**
 * 参数不合法
 * @author hualuomoli
 *
 */
public class InvalidParameterException extends MvcException {

	private static final long serialVersionUID = -709787577502299447L;

	public InvalidParameterException(String msg) {
		this(ERROR_PARAMETER_INVALID, msg);
	}

	public InvalidParameterException(String code, String msg) {
		super(code, msg);
	}

	public InvalidParameterException(ErrorData errorData, Throwable cause) {
		super(errorData, cause);
	}

	public InvalidParameterException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public InvalidParameterException(ErrorData errorData) {
		super(errorData);
	}

}
