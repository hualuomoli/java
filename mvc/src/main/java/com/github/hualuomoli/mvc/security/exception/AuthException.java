package com.github.hualuomoli.mvc.security.exception;

/**
 * 权限异常
 * @author hualuomoli
 *
 */
public class AuthException extends MvcException {

	private static final long serialVersionUID = 879054420696384909L;

	@Override
	public int getStatus() {
		return 401;
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(String errorCode, String message) {
		super(errorCode, message);
	}

	public AuthException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

}
