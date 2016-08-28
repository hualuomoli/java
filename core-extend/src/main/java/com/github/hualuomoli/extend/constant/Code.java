package com.github.hualuomoli.extend.constant;

import com.github.hualuomoli.exception.CodeException.CodeError;

// core-extend使用102
public enum Code implements CodeError {

	// 1020
	ROLE_NOT_FOUND(10201, "角色不存在"), //
	ROLE_ALREADY_EXISTS(10202, "角色编码已存在"), //

	// 1021
	USER_NOT_FOUND(10211, "用户不存在"), //
	USER_INVALID_USERNAME_OR_PASSWORD(10212, "用户名或密码错误"), //
	USER_NOT_NOMAL(10213, "用户非正常状态"), //
	;

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
