package com.github.hualuomoli.plugin;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.constant.Charset;

import redis.clients.jedis.Jedis;

/**
 * Redis缓存
 * @author hualuomoli
 *
 */
public class RedisCache implements SerializeCache {

	private static final String OK = "OK";

	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	private byte[] getKey(String key) {
		return key.getBytes(Charset.UTF8);
	}

	@Override
	public boolean success() {
		return jedis != null && jedis.isConnected();
	}

	@Override
	public boolean set(String key, byte[] data) {
		return this.set(key, data, MAX_EXP);
	}

	@Override
	public boolean set(String key, byte[] data, int expire) {
		String success = jedis.setex(this.getKey(key), expire, data);
		return StringUtils.equals(OK, success);
	}

	@Override
	public byte[] get(String key) {
		return jedis.get(this.getKey(key));
	}

	@Override
	public boolean remove(String key) {
		if (!this.exists(key)) {
			return true;
		}
		return jedis.del(this.getKey(key)) == 1;
	}

	@Override
	public boolean exists(String key) {
		return jedis.exists(this.getKey(key));
	}

	@Override
	public long plus(String key, long number) {
		return jedis.incrBy(this.getKey(key), number);
	}

	@Override
	public void empty() {
		jedis.flushAll();
	}

	@Override
	public boolean set(String key, Serializable serializable) {
		return this.set(key, SerializationUtils.serialize(serializable));
	}

	@Override
	public boolean set(String key, Serializable serializable, int expire) {
		return this.set(key, SerializationUtils.serialize(serializable), expire);
	}

	@Override
	public <T extends Serializable> T getObject(String key) {
		return SerializationUtils.deserialize(this.get(key));
	}

	@Override
	public boolean set(String key, String data) {
		return this.set(key, data.getBytes(Charset.UTF8));
	}

	@Override
	public boolean set(String key, String data, int expire) {
		return this.set(key, data.getBytes(Charset.UTF8), expire);
	}

	@Override
	public String getString(String key) {
		return new String(this.get(key), Charset.UTF8);
	}
}
