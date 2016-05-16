package com.github.hualuomoli.raml.exception;

/**
 * 解析异常
 * @author hualuomoli
 *
 */
public class ParserException extends RuntimeException {

	private static final long serialVersionUID = -5373820481438857441L;

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

}
