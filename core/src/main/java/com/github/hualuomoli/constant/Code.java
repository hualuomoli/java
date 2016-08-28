package com.github.hualuomoli.constant;

import com.github.hualuomoli.exception.CodeException.CodeError;

// core使用101开头
public enum Code implements CodeError {

	TOKEN_EMPTY(10101, "令牌为空"), //
	TOKEN_OVER_TIME(10102, "令牌超时"),;

	private Integer code;
	private String message;

	private Code(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
