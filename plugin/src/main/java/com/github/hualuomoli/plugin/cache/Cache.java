package com.github.hualuomoli.plugin.cache;

/**
 * 缓存
 * @author hualuomoli
 *
 */
public interface Cache {

	/** 是否成功可以使用 */
	boolean success();

	/**
	 * 设置值
	 * @param key key
	 * @param data 值
	 * @return 是否设置成功
	 */
	boolean set(String key, byte[] data);

	/**
	 * 设置值
	 * @param key key
	 * @param data 值
	 * @param expire 有效时长,单位为秒
	 * @return 是否设置成功
	 */
	boolean set(String key, byte[] data, int expire);

	/** 获取值 */
	byte[] get(String key);

	/** 获取值并重新刷新缓存 */
	byte[] getAndRefresh(String key);

	/** 获取值并重新刷新缓存 */
	byte[] getAndRefresh(String key, int expire);

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
	Long plus(String key, Long number);

	/** 清空缓存 */
	void empty();

}
