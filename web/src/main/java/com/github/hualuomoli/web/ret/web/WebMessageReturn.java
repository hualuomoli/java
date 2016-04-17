package com.github.hualuomoli.web.ret.web;

import com.github.hualuomoli.web.ret.MessageReturn;

/**
 * web return message
 * status use success
 * @author hualuomoli
 *
 */
public abstract class WebMessageReturn implements MessageReturn {

	private static final long serialVersionUID = 2667643904151187508L;

	protected boolean success;

	public WebMessageReturn() {
	}

	public WebMessageReturn(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
