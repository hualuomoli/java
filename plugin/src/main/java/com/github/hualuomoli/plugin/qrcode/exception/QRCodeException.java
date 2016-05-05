package com.github.hualuomoli.plugin.qrcode.exception;

/**
 * 二维码异常
 * @author hualuomoli
 *
 */
public class QRCodeException extends Exception {

	private static final long serialVersionUID = 4440054145053540769L;

	public QRCodeException() {
		super();
	}

	public QRCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QRCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public QRCodeException(String message) {
		super(message);
	}

	public QRCodeException(Throwable cause) {
		super(cause);
	}

}
