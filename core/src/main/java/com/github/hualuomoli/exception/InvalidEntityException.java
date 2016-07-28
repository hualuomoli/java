package com.github.hualuomoli.exception;

import java.util.List;

/**
 * 不合法的实体类
 * @author hualuomoli
 *
 */
public class InvalidEntityException extends RuntimeException {

	private static final long serialVersionUID = 1935331470749019838L;

	private List<String> errors;

	public InvalidEntityException() {
	}

	public InvalidEntityException(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

}
