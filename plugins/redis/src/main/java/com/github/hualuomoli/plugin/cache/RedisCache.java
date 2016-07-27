package com.github.hualuomoli.plugin.cache;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.constant.Charset;

import redis.clients.jedis.Jedis;

/**
 * Redis缓存
 * @author hualuomoli
 *
 */
public class RedisCache extends SerializeCacheAbstract {

	private static final String OK = "OK";
	private static Boolean success = null;

	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	// 获取key的二进制
	private byte[] getKey(String key) {
		return key.getBytes(Charset.UTF8);
	}

	// 是否返回成功
	private boolean isReturnSuccess(String success) {
		return StringUtils.equals(OK, success);
	}

	@Override
	public boolean success() {
		if (success == null) {
			synchronized (OK) {
				if (success == null) {
					success = jedis != null && jedis.isConnected();
				}
			}
		}
		return success;
	}

	@Override
	public boolean setValue(String key, byte[] data, int expire) {
		String success = jedis.setex(this.getKey(key), expire, data);
		return this.isReturnSuccess(success);
	}

	@Override
	public byte[] getValue(String key) {
		return jedis.get(this.getKey(key));
	}

	@Override
	public boolean removeKey(String key) {
		if (!this.keyExists(key)) {
			return true;
		}
		return jedis.del(this.getKey(key)) == 1;
	}

	@Override
	public boolean keyExists(String key) {
		return jedis.exists(this.getKey(key));
	}

	@Override
	public Long plusNumber(String key, Long number) {
		return jedis.incrBy(this.getKey(key), number);
	}

	@Override
	public void clear() {
		jedis.flushAll();
	}

}
