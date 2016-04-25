package com.github.hualuomoli.ret.none;

import com.github.hualuomoli.commons.json.JsonMapper;
import com.github.hualuomoli.ret.MessageReturn;

/**
 * no data return message
 * @description 
 * 成功:
 * {
 * 	"code" : "0"
 * }
 * 失败
 * {
 * 	"code" : "50001",
 * 	"message" : "username or password error."
 * }
 * @author hualuomoli
 *
 */
public class NoDataMessageReturn implements MessageReturn {

	private static final long serialVersionUID = -2270964264902534081L;
	public static final String CODE_SUCCESS = "0";

	private String code;
	private String message;

	public NoDataMessageReturn() {
	}

	public NoDataMessageReturn(String code) {
		this.code = code;
	}

	public NoDataMessageReturn(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toJson() {
		return JsonMapper.toJsonString(this);
	}

}
