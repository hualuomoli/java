package com.github.hualuomoli.plugin.cache;

import java.io.Serializable;

/**
 * 缓存
 * @author hualuomoli
 *
 */
public interface Cache {

	public static final int MAX_EXP = 30 * 24 * 60 * 60; // 30 days

	/** 是否已经实例化 */
	boolean isInstance();

	/**
	 * 设置值，默认30天 {@link #MAX_EXP}
	 * @param key key
	 * @param serializable 值
	 * @return 是否设置成功
	 */
	boolean set(String key, Serializable serializable);

	/**
	 * 设置值
	 * @param key key
	 * @param serializable 值
	 * @param expire 有效时长,单位为秒
	 * @return 是否设置成功
	 */
	boolean set(String key, Serializable serializable, int expire);

	/** 获取值 */
	<T> T get(String key);

	/** 删除 */
	boolean remove(String key);

	/** 是否存在 */
	boolean exists(String key);

	/**
	 * 增加
	 * @param key key
	 * @param number
	 * @return 增加后的值
	 */
	long plus(String key, long number);

	/** 清空缓存 */
	void empty();

}
