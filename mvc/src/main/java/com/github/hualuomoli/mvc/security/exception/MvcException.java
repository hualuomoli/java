package com.github.hualuomoli.mvc.security.exception;

import com.github.hualuomoli.mvc.exception.entity.ErrorData;

/**
 * MVC异常
 * @author hualuomoli
 *
 */
public class MvcException extends RuntimeException {

	private static final long serialVersionUID = -6936931609600847923L;

	public static final String ERROR_AUTH_NO_LOGIN = "9999"; // 未登录
	public static final String ERROR_AUTH_OVERTIME = "9998"; // 登录超时
	public static final String ERROR_USER_INVALID = "9989"; // 用户名或密码错误

	public static final String ERROR_PARAMETER_INVALID = "9979"; // 参数不合法

	private ErrorData errorData;

	public MvcException(String code, String msg) {
		this(new ErrorData(code, msg));
	}

	public MvcException(ErrorData errorData) {
		super(errorData.getMsg());
		this.errorData = errorData;

	}

	public MvcException(String code, String msg, Throwable cause) {
		this(new ErrorData(code, msg), cause);
	}

	public MvcException(ErrorData errorData, Throwable cause) {
		super(errorData.getMsg(), cause);
		this.errorData = errorData;
	}

	public ErrorData getErrorData() {
		return errorData;
	}

	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}

}
