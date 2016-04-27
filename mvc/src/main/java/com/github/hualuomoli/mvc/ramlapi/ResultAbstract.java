package com.github.hualuomoli.mvc.ramlapi;

import com.github.hualuomoli.commons.json.JsonMapper;

/**
 * 返回结果,实现toJson方法
 * @author hualuomoli
 *
 */
public abstract class ResultAbstract implements Result {

	public static final String CODE_SUCCESS = "0";
	public static final String MSG_SUCCESS = "success";

	protected String code; // 响应编码
	protected String msg; // 响应信息

	public ResultAbstract() {
	}

	@Override
	public String toJson() {
		return JsonMapper.toJsonString(this);
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

}
