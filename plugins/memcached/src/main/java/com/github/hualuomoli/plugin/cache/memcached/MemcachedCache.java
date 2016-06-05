package com.github.hualuomoli.plugin.cache.memcached;

import java.io.Serializable;
import java.util.Date;

import com.github.hualuomoli.plugin.cache.Cache;
import com.whalin.MemCached.MemCachedClient;

/**
 * Memcached
 * @author hualuomoli
 *
 */
public class MemcachedCache implements Cache {

	private MemCachedClient memCachedClient;

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	@Override
	public boolean isInstance() {
		return memCachedClient != null;
	}

	@Override
	public boolean set(String key, Serializable serializable) {
		return memCachedClient.set(key, serializable);
	}

	@Override
	public boolean set(String key, Serializable serializable, int expire) {
		return memCachedClient.set(key, serializable, new Date(expire * 1000));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T) memCachedClient.get(key);
	}

	@Override
	public boolean remove(String key) {
		return memCachedClient.delete(key);
	}

	@Override
	public boolean exists(String key) {
		return memCachedClient.keyExists(key);
	}

	@Override
	public long plus(String key, long number) {
		if (!exists(key)) {
			memCachedClient.setPrimitiveAsString(true);
			set(key, 0L);
		}
		return memCachedClient.incr(key, number);
	}

	@Override
	public void empty() {
		memCachedClient.flushAll();
	}

}
