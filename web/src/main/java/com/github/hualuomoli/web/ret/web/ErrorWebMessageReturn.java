package com.github.hualuomoli.web.ret.web;

/**
 * web return error message
 * @author hualuomoli
 *
 */
public class ErrorWebMessageReturn extends WebMessageReturn {

	private static final long serialVersionUID = -4785769138277342424L;

	private String message;

	public ErrorWebMessageReturn() {
		super(false);
	}

	public ErrorWebMessageReturn(String message) {
		super(false);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
