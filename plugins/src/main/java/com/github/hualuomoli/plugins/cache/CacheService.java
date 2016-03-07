package com.github.hualuomoli.plugins.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存服务
 *
 */
public interface CacheService {

	public static final Logger logger = LoggerFactory.getLogger(CacheService.class);
	public static final int MAX_EXP = 30 * 24 * 60 * 60; // 30 days

	/** 是否已经实例化 */
	boolean isOk();

	/** 设置值 */
	boolean set(String key, Object value);

	/** 设置值 */
	boolean set(String key, int exp, Object value);

	/** 获取值 */
	<T> T get(String key);

	/** 加 */
	Long plus(String key, Long number);

}
