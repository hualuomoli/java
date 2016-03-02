package com.github.hualuomoli.plugins.cache.memcached;

import com.github.hualuomoli.plugins.cache.CacheService;

import net.rubyeye.xmemcached.MemcachedClient;

public class MemcachedClientService implements CacheService {

	private MemcachedClient memcachedClient;

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public boolean isInitialized() {
		return true;
	}

	@Override
	public boolean set(String key, Object value) {
		return this.set(key, MAX_EXP, value);
	}

	@Override
	public boolean set(String key, int exp, Object value) {
		try {
			return memcachedClient.set(key, exp, value);
		} catch (Exception e) {
			logger.error("set message inot {} error.{}", key, e.getMessage());
			return false;
		}
	}

	@Override
	public <T> T get(String key) {
		try {
			return memcachedClient.get(key);
		} catch (Exception e) {
			logger.error("get message inot {} error.{}", key, e.getMessage());
			return null;
		}
	}

	@Override
	public Long plus(String key, Long number) {
		try {
			return memcachedClient.incr(key, number);
		} catch (Exception e) {
			logger.error("plus message inot {} error.{}", key, e.getMessage());
			return number;
		}
	}

}
