package com.github.hualuomoli.exception;

/**
 * 数据未找到
 * @author hualuomoli
 *
 */
public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = -5344296413957063789L;

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}

}
