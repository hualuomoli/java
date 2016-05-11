package com.github.hualuomoli.commons.template.exception;

/**
 * 异常
 * @author hualuomoli
 *
 */
public class TemplateException extends RuntimeException {

	private static final long serialVersionUID = 5142109684488111266L;

	public TemplateException(String message) {
		super(message);
	}

	public TemplateException(Throwable cause) {
		super(cause);
	}

}
