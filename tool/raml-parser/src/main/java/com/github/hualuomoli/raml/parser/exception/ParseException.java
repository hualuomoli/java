package com.github.hualuomoli.raml.parser.exception;

/**
 * 解析错误
 * @author hualuomoli
 *
 */
public class ParseException extends Exception {

	private static final long serialVersionUID = 4007270995191362625L;

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
