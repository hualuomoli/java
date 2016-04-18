package com.github.hualuomoli.ret.login;

import com.github.hualuomoli.ret.none.NoDataMessageReturn;

/**
 * 登陆
 * @author hualuomoli
 *
 */
public class LoginMessageReturn extends NoDataMessageReturn {

	private static final long serialVersionUID = -5005633861046694799L;

	private String token;

	public LoginMessageReturn() {
	}

	public LoginMessageReturn(String code) {
		super(code);
	}

	public LoginMessageReturn(String code, String message) {
		super(code, message);
	}

	public LoginMessageReturn(String code, String message, String token) {
		super(code, message);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
