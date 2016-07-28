package com.github.hualuomoli.exception;

/**
 * 期望返回一条数据,但返回了多条数据
 * @author hualuomoli
 *
 */
public class MoreDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 4092349452203769143L;

	public MoreDataFoundException() {
		super();
	}

	public MoreDataFoundException(String message) {
		super(message);
	}

	public MoreDataFoundException(Throwable cause) {
		super(cause);
	}

}
