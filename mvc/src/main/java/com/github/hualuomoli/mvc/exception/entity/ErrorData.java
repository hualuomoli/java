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

	public static final String CODE = "code";
	public static final String MSG = "msg";

	private String code;
	private String msg;

	public ErrorData() {
	}

	public ErrorData(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toJson() {
		return JsonMapper.toJsonString(this);
	}

}
