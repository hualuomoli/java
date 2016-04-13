package com.github.hualuomoli.plugins.cache;

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
	 * 设置值，默认永久有效
	 * @param key key
	 * @param value 值
	 * @return 是否设置成功
	 */
	boolean set(String key, Object value);

	/**
	 * 设置值，默认永久有效
	 * @param key key
	 * @param value 值
	 * @param expire 有效时长,单位为秒
	 * @return 是否设置成功
	 */
	boolean set(String key, Object value, int expire);

	/** 获取值 */
	<T> T get(String key);

	/** 删除 */
	boolean remove(String key);

	/** 是否存在 */
	boolean exists(String key);

	/**
	 * 增加1
	 * @param key key
	 * @return 增加1后的值
	 */
	long plus(String key);

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
