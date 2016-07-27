package com.github.hualuomoli.plugin.mq;

/**
 * 消息队列异常
 * @author hualuomoli
 *
 */
public class MessageException extends RuntimeException {

	private static final long serialVersionUID = -5818212759493369254L;

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

	public MessageException() {
		super();
	}

}
