package com.github.hualuomoli.plugin.cache;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.SerializeUtils;

/**
 * 添加使用默认有效时间的方法
 * @author hualuomoli
 *
 */
public abstract class SerializeCacheAdaptor implements SerializeCache {

	protected static final Logger logger = LoggerFactory.getLogger(SerializeCacheAdaptor.class);

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

	private boolean validCache() {
		return this.success();
	}

	// 有效时间
	protected boolean validExpire(Integer expire) {
		// 没有设置时间
		if (expire == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("expire must be not null.");
			}
			return false;
		}
		// 时间设置太小
		if (expire <= 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("expire must greater than zero.");
			}
			return false;
		}
		// 时间太大
		if (expire > this.getMaxExpire()) {
			if (logger.isWarnEnabled()) {
				logger.warn("expire must less than " + this.getMaxExpire() + ".");
			}
			return false;
		}
		return true;
	}

	// key是否有效
	protected boolean validKey(String key) {
		if (StringUtils.isBlank(key)) {
			if (logger.isWarnEnabled()) {
				logger.warn("key must be not null.");
			}
			return false;
		}
		return true;
	}

	// data是否有效
	protected boolean validData(byte[] data) {
		if (data == null || data.length == 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("data must be not empty.");
			}
			return false;
		}
		return true;
	}

	// data是否有效
	protected boolean validData(Serializable serializable) {
		if (serializable == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("data must be not null.");
			}
			return false;
		}
		return true;
	}

	// number是否有效
	protected boolean validNumber(Long number) {
		if (number == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("number must be not null.");
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean set(String key, byte[] data) {
		return this.set(key, data, this.getDefaultExpire());
	}

	@Override
	public boolean set(String key, byte[] data, int expire) {
		if (this.validCache() //
				&& this.validExpire(expire) //
				&& this.validKey(key) //
				&& this.validData(data)) {
			return this.setValue(this.prefix + key, data, expire);
		}
		return false;
	}

	@Override
	public boolean setSerializable(String key, Serializable serializable) {
		return this.setSerializable(key, serializable, this.getDefaultExpire());
	}

	@Override
	public boolean setSerializable(String key, Serializable serializable, int expire) {
		if (this.validCache() //
				&& this.validExpire(expire) //
				&& this.validKey(key) //
				&& this.validData(serializable)) {
			return this.setSerializableValue(this.prefix + key, serializable, expire);
		}
		return false;
	}

	@Override
	public byte[] get(String key) {
		if (this.validCache() && this.validKey(key)) {
			return this.getValue(this.prefix + key);
		}
		return null;
	}

	@Override
	public <T extends Serializable> T getSerializable(String key) {
		if (this.validCache() && this.validKey(key)) {
			return this.getSerializableValue(this.prefix + key);
		}
		return null;
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
		T t = this.getSerializable(key);
		if (t != null) {
			this.setSerializableValue(key, t, this.getDefaultExpire());
		}
		return t;
	}

	@Override
	public boolean remove(String key) {
		if (this.validCache() && this.validKey(key)) {
			return this.removeKey(this.prefix + key);
		}
		return false;
	}

	@Override
	public boolean exists(String key) {
		if (this.validCache() && this.validKey(key)) {
			return this.keyExists(this.prefix + key);
		}
		return false;
	}

	@Override
	public Long plus(String key, Long number) {
		if (this.validCache() //
				&& this.validKey(key) //
				&& this.validNumber(number)) {
			return this.plusNumber(this.prefix + key, number);
		}
		return 0L;
	}

	@Override
	public void empty() {
		this.validCache();
		this.clear();
	}

	// 设置值
	public abstract boolean setValue(String key, byte[] data, int expire);

	// 设置序列化值
	public boolean setSerializableValue(String key, Serializable serializable, int expire) {
		return this.setValue(key, SerializeUtils.serialize(serializable), expire);
	}

	// 获取值
	public abstract byte[] getValue(String key);

	// 获取序列化值
	public <T extends Serializable> T getSerializableValue(String key) {
		return SerializeUtils.unserialize(this.getValue(key));
	}

	// 删除
	public abstract boolean removeKey(String key);

	// 是否存在
	public abstract boolean keyExists(String key);

	// 数值叠加
	public abstract Long plusNumber(String key, Long number);

	// 清空
	public abstract void clear();

}
