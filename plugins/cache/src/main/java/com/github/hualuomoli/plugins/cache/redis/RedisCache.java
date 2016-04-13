package com.github.hualuomoli.plugins.cache.redis;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.serialize.SerializeUtils;
import com.github.hualuomoli.plugins.cache.Cache;

import redis.clients.jedis.Jedis;

public class RedisCache implements Cache {

	private static final String OK = "OK";

	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	@Override
	public boolean isInstance() {
		return jedis != null && jedis.isConnected();
	}

	@Override
	public boolean set(String key, Object value) {
		String success = jedis.set(key.getBytes(), SerializeUtils.serialize(value));
		return StringUtils.equals(OK, success);
	}

	@Override
	public boolean set(String key, Object value, int expire) {
		String success = jedis.setex(key.getBytes(), expire, SerializeUtils.serialize(value));
		return StringUtils.equals(OK, success);
	}

	@Override
	public <T> T get(String key) {
		return SerializeUtils.unserialize(jedis.get(key.getBytes()));
	}

	@Override
	public boolean remove(String key) {
		if (!this.exists(key)) {
			return true;
		}
		return jedis.del(key) == 1;
	}

	@Override
	public boolean exists(String key) {
		return jedis.exists(key);
	}

	@Override
	public long plus(String key) {
		return this.plus(key, 1);
	}

	@Override
	public long plus(String key, long number) {
		return jedis.incrBy(key, number);
	}

	@Override
	public void empty() {
		jedis.flushAll();
	}
}
