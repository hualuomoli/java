package com.github.hualuomoli.plugins.jpush.exception;

public class JPushException extends RuntimeException {

	private static final long serialVersionUID = -3936846821388041298L;

	public JPushException() {
		super();
	}

	public JPushException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JPushException(String message, Throwable cause) {
		super(message, cause);
	}

	public JPushException(String message) {
		super(message);
	}

	public JPushException(Throwable cause) {
		super(cause);
	}

}
