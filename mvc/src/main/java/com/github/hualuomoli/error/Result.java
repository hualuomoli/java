package com.github.hualuomoli.error;

/**
 * 系统错误 100以下
 * @author hualuomoli
 *
 */
public enum Result {

	// user 1 - 10
	USER_INVALID(1, "user invalid"), // 用户名或密码错误
	USER_NO_LOGIN(2, "user no login"), // 用户未登录
	USER_OVERTIME(3, "user overtime"), // 用户登录超时
	;

	private int code;
	private String message;

	private Result(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
