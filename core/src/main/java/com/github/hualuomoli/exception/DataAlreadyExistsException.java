package com.github.hualuomoli.exception;

/**
 * 数据已经存在
 * @author hualuomoli
 *
 */
public class DataAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -5344296413957063789L;

	public DataAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAlreadyExistsException(String message) {
		super(message);
	}

	public DataAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
