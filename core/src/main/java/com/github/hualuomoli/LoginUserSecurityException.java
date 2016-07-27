package com.github.hualuomoli;

public class LoginUserSecurityException extends RuntimeException {

	private static final long serialVersionUID = 8881556446167199145L;

	public static final LoginUserSecurityException NO_TOKEN = new LoginUserSecurityException();
	public static final LoginUserSecurityException TOKEN_OVER_TIME = new LoginUserSecurityException();

	public LoginUserSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginUserSecurityException(String message) {
		super(message);
	}

	public LoginUserSecurityException(Throwable cause) {
		super(cause);
	}

	public LoginUserSecurityException() {
	}

}
