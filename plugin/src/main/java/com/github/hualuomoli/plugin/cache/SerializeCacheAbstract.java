package com.github.hualuomoli.plugin.cache;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.SerializeUtils;

/**
 * 添加使用默认有效时间的方法
 * @author hualuomoli
 *
 */
public abstract class SerializeCacheAbstract implements SerializeCache {

	private static final Integer DEFAULT_EXPIRE = 30 * 60; // 30 minute
	private static final Integer MAX_EXPIRE = 30 * 24 * 60 * 60; // 30 day

	private String prefix = "";

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getDefaultExpire() {
		return DEFAULT_EXPIRE;
	}

	public Integer getMaxExpire() {
		return MAX_EXPIRE;
	}

	private void validCache() {
		if (!this.success()) {
			throw new CacheException("cache is invalid.");
		}
	}

	// 有效时间
	protected void validExpire(Integer expire) {
		if (expire == null) {
			throw new CacheException("expire must be not null.");
		}
		if (expire <= 0) {
			throw new CacheException("expire must greater than zero.");
		}
		if (expire > this.getMaxExpire()) {
			throw new CacheException("expire must less than " + this.getMaxExpire() + ".");
		}
	}

	// key是否有效
	protected void validKey(String key) {
		if (StringUtils.isBlank(key)) {
			throw new CacheException("key must be not null.");
		}
	}

	// data是否有效
	protected void validData(byte[] data) {
		if (data == null || data.length == 0) {
			throw new CacheException("data must be not empty.");
		}
	}

	// data是否有效
	protected void validData(Serializable serializable) {
		if (serializable == null) {
			throw new CacheException("data must be not null.");
		}
	}

	// number是否有效
	protected void validNumber(Long number) {
		if (number == null) {
			throw new CacheException("number must be not null.");
		}
	}

	public boolean set(String key, byte[] data) {
		return this.set(key, data, this.getDefaultExpire());
	}

	@Override
	public boolean set(String key, byte[] data, int expire) {
		this.validCache();
		this.validKey(key);
		this.validData(data);
		this.validExpire(expire);
		return this.setValue(this.prefix + key, data, expire);
	}

	public boolean set(String key, Serializable serializable) {
		return this.set(key, serializable, this.getDefaultExpire());
	}

	@Override
	public boolean set(String key, Serializable serializable, int expire) {
		this.validCache();
		this.validKey(key);
		this.validData(serializable);
		this.validExpire(expire);
		return this.setValue(this.prefix + key, serializable, expire);
	}

	@Override
	public byte[] get(String key) {
		this.validCache();
		this.validKey(key);
		return this.getValue(this.prefix + key);
	}

	@Override
	public <T extends Serializable> T getSerializable(String key) {
		this.validCache();
		this.validKey(key);
		return this.getSerializableValue(this.prefix + key);
	}

	@Override
	public byte[] getAndRefresh(String key) {
		byte[] bytes = this.get(key);
		if (bytes != null && bytes.length > 0) {
			this.set(key, bytes);
		}
		return bytes;
	}

	@Override
	public <T extends Serializable> T getSerializableAndRefresh(String key) {
		T t = this.getSerializableAndRefresh(key);
		if (t != null) {
			this.set(key, t);
		}
		return t;
	}

	@Override
	public boolean remove(String key) {
		this.validCache();
		this.validKey(key);
		return this.removeKey(this.prefix + key);
	}

	@Override
	public boolean exists(String key) {
		this.validCache();
		this.validKey(key);
		return this.keyExists(this.prefix + key);
	}

	@Override
	public Long plus(String key, Long number) {
		this.validCache();
		this.validKey(key);
		this.validNumber(number);
		return this.plusNumber(this.prefix + key, number);
	}

	@Override
	public void empty() {
		this.validCache();
		this.clear();
	}

	public boolean setValue(String key, byte[] data) {
		return this.setValue(key, data, this.getDefaultExpire());
	}

	public abstract boolean setValue(String key, byte[] data, int expire);

	public boolean setValue(String key, Serializable serializable) {
		return this.setValue(key, serializable, this.getDefaultExpire());
	}

	public boolean setValue(String key, Serializable serializable, int expire) {
		return this.setValue(key, SerializeUtils.serialize(serializable), expire);
	}

	public abstract byte[] getValue(String key);

	public <T extends Serializable> T getSerializableValue(String key) {
		return SerializeUtils.unserialize(this.getValue(key));
	}

	public abstract boolean removeKey(String key);

	public abstract boolean keyExists(String key);

	public abstract Long plusNumber(String key, Long number);

	public abstract void clear();

}
