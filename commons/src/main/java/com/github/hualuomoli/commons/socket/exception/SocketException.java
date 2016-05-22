package com.github.hualuomoli.commons.socket.exception;

/**
 * Socket异常
 * @author hualuomoli
 *
 */
public class SocketException extends RuntimeException {

	private static final long serialVersionUID = 1187399769571401342L;

	public SocketException(String message) {
		super(message);
	}

	public SocketException(Throwable cause) {
		super(cause);
	}

}
