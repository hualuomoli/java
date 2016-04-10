package com.github.hualuomoli.plugins.cache.redis;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.serialize.SerializeUtil;
import com.github.hualuomoli.plugins.cache.CacheService;

import redis.clients.jedis.Jedis;

public class RedisCacheService implements CacheService {

	private static final String OK = "OK";

	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void init() {
	}

	@Override
	public boolean isOk() {
		return jedis != null && jedis.isConnected();
	}

	@Override
	public boolean set(String key, Object value) {
		String success = jedis.set(key.getBytes(), this.serialize(value));
		return StringUtils.equals(OK, success);
	}

	@Override
	public boolean set(String key, Object value, int exp) {
		String success = jedis.setex(key.getBytes(), exp, this.serialize(value));
		return StringUtils.equals(OK, success);
	}

	@Override
	public <T> T get(String key) {
		return this.unserialize(jedis.get(key.getBytes()));
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long plus(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean keyExists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long plus(String key, long number) {
		return jedis.incrBy(key.getBytes(), number);
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub

	}

	//////////////////////////////////////////

	private byte[] serialize(Object value) {
		return SerializeUtil.serialize(value);
	}

	private <T> T unserialize(byte[] bytes) {
		return SerializeUtil.unserialize(bytes);
	}

}
