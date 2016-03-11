package com.github.hualuomoli.plugins.cache.memcache;

import java.util.Date;

import com.github.hualuomoli.plugins.cache.CacheService;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcacheCacheService implements CacheService {

	private static final Date MAX_EXPIRY = getExpDate(MAX_EXP);

	private SockIOPool memCachedSockIOPool;
	private MemCachedClient memCachedClient;

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	public void setMemCachedSockIOPool(SockIOPool memCachedSockIOPool) {
		this.memCachedSockIOPool = memCachedSockIOPool;
	}

	@Override
	public boolean isOk() {
		return memCachedSockIOPool != null && memCachedSockIOPool.isInitialized();
	}

	@Override
	public boolean set(String key, Object value) {
		return this.set(key, value, MAX_EXP);
	}

	@Override
	public boolean set(String key, Object value, int exp) {
		return memCachedClient.set(key, value, exp == MAX_EXP ? MAX_EXPIRY : getExpDate(exp));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T) memCachedClient.get(key);
	}

	@Override
	public boolean delete(String key) {
		return memCachedClient.delete(key);
	}

	@Override
	public boolean keyExists(String key) {
		return memCachedClient.keyExists(key);
	}

	@Override
	public long plus(String key) {
		return this.plus(key, 1);
	}

	@Override
	public long plus(String key, long number) {
		if (!this.keyExists(key)) {
			memCachedClient.set(key, "0");
		}
		return memCachedClient.incr(key, number);
	}

	@Override
	public void clearAll() {
		memCachedClient.flushAll();
	}

	private static Date getExpDate(int exp) {
		return new Date(exp);
	}

}
