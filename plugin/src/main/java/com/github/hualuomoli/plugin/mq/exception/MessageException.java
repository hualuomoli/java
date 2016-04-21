package com.github.hualuomoli.plugin.mq.exception;

/**
 * 消息服务异常
 * @author hualuomoli
 *
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = -5233130376744856436L;

	public MessageException() {
		super();
	}

	public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

}
