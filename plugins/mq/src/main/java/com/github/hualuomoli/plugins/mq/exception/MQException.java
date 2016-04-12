package com.github.hualuomoli.plugins.mq.exception;

/**
 * MQ exception
 * @author hualuomoli
 *
 */
public class MQException extends Exception {

	private static final long serialVersionUID = -5233130376744856436L;

	public MQException() {
		super();
	}

	public MQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MQException(String message, Throwable cause) {
		super(message, cause);
	}

	public MQException(String message) {
		super(message);
	}

	public MQException(Throwable cause) {
		super(cause);
	}

}
