package com.github.hualuomoli.exception;

/**
 * 指定编码和名称的异常
 * @author hualuomoli
 *
 */
public class CodeException extends RuntimeException {

	private static final long serialVersionUID = -1990636437440096628L;

	private CodeError codeError;

	public CodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeException(String message) {
		super(message);
	}

	public CodeException(CodeError codeError) {
		super();
		this.codeError = codeError;
	}

	public CodeError getCodeError() {
		return codeError;
	}

	// 错误
	public static interface CodeError {

		// 错误编码
		Integer getCode();

		// 错误名称
		String getMessage();

	}

}
