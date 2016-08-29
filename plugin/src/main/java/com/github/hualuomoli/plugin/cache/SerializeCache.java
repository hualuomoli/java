package com.github.hualuomoli.plugin.cache;

import java.io.Serializable;

/**
 * 序列化接口
 * @author hualuomoli
 *
 */
public interface SerializeCache extends Cache {

	/**
	 * 设置值
	 * @param key key
	 * @param serializable 值
	 * @return 是否设置成功
	 */
	boolean setSerializable(String key, Serializable serializable);

	/**
	 * 设置值
	 * @param key key
	 * @param serializable 值
	 * @param expire 有效时长,单位为秒
	 * @return 是否设置成功
	 */
	boolean setSerializable(String key, Serializable serializable, int expire);

	/** 获取值 */
	<T extends Serializable> T getSerializable(String key);

	/** 获取值并重新刷新缓存 */
	<T extends Serializable> T getSerializableAndRefresh(String key);

	/** 获取值并重新刷新缓存 */
	<T extends Serializable> T getSerializableAndRefresh(String key, int expire);

}
