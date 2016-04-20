package com.github.hualuomoli.mvc.parse.exception;

/**
 * 参数解析异常
 * @author hualuomoli
 *
 */
public class ParseException extends Exception {

	private static final long serialVersionUID = 367849784306788511L;

	public ParseException() {
		super();
	}

	public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

}
