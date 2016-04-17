package com.github.hualuomoli.web.ret.api;

/**
 * API return error message
 * @author hualuomoli
 *
 */
public class ErrorApiMessageReturn extends ApiMessageReturn {

	private static final long serialVersionUID = -4785769138277342424L;

	private String message;

	public ErrorApiMessageReturn() {
	}

	public ErrorApiMessageReturn(String code) {
		super(code);
	}

	public ErrorApiMessageReturn(String code, String message) {
		super(code);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
