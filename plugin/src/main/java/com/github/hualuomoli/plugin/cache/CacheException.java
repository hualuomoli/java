package com.github.hualuomoli.plugin.cache;

/**
 * 缓存处理时的异常
 * @author hualuomoli
 *
 */
public class CacheException extends RuntimeException {

	private static final long serialVersionUID = -7120574735507763973L;

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}
