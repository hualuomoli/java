package com.github.hualuomoli.plugin.cache;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

public class DefaultSerializeCache extends SerializeCacheAdaptor {

	private Map<String, Object> dataMap = Maps.newHashMap();
	private Map<String, Long> expireMap = Maps.newHashMap();

	@Override
	public boolean success() {
		return true;
	}

	@Override
	public boolean setValue(String key, byte[] data, int expire) {
		// 设置有效时间
		expireMap.put(key, expire * 1000 + System.currentTimeMillis());
		// 设置数据
		dataMap.put(key, data);
		return true;
	}

	@Override
	public boolean setValue(String key, Serializable serializable, int expire) {
		// 设置有效时间
		expireMap.put(key, expire * 1000 + System.currentTimeMillis());
		// 设置数据
		dataMap.put(key, serializable);
		return true;
	}

	@Override
	public byte[] getValue(String key) {
		if (this.keyExists(key)) {
			return (byte[]) dataMap.get(key);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T getSerializableValue(String key) {
		if (this.keyExists(key)) {
			return (T) dataMap.get(key);
		} else {
			return null;
		}
	}

	@Override
	public boolean removeKey(String key) {
		expireMap.remove(key);
		dataMap.remove(key);
		return true;
	}

	@Override
	public boolean keyExists(String key) {
		Long expire = expireMap.get(key);

		// 没有值
		if (expire == null) {
			return false;
		}

		if (expire > System.currentTimeMillis()) {
			// 值有效
			return true;
		} else {
			// 值无效
			this.removeKey(key);
			return false;
		}
	}

	@Override
	public Long plusNumber(String key, Long number) {
		Long value = this.getSerializableValue(key);
		Long ret = 0L;
		if (value == null) {
			ret = 1L;
		} else {
			ret = value + number;
		}
		this.setValue(key, ret);
		return ret;
	}

	@Override
	public void clear() {
		expireMap.clear();
		dataMap.clear();
	}

}
