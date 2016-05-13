package com.github.hualuomoli.mvc.auth;

/**
 * 权限异常
 * @author hualuomoli
 *
 */
public class AuthException extends RuntimeException {

	public static final AuthException NO_LOGIN = new AuthException("no login");
	public static final AuthException OVER_TIME = new AuthException("over time");

	private static final long serialVersionUID = -1108537416225572051L;

	private AuthException(String message) {
		super(message);
	}

}
