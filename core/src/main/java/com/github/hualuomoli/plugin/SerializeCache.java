package com.github.hualuomoli.plugin;

import java.io.Serializable;

/**
 * 序列化接口
 * @author hualuomoli
 *
 */
public interface SerializeCache extends Cache {

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
	<T extends Serializable> T getObject(String key);

	/**
	 * 设置值，默认30天 {@link #MAX_EXP}
	 * @param key key
	 * @param data 值
	 * @return 是否设置成功
	 */
	boolean set(String key, String data);

	/**
	 * 设置值
	 * @param key key
	 * @param data 值
	 * @param expire 有效时长,单位为秒
	 * @return 是否设置成功
	 */
	boolean set(String key, String data, int expire);

	/** 获取值 */
	String getString(String key);

}
