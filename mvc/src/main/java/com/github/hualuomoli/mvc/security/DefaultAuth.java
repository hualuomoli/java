package com.github.hualuomoli.mvc.security;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.github.hualuomoli.plugin.cache.Cache;
import com.google.common.collect.Maps;

/**
 * 默认权限验证
 * @author hualuomoli
 *
 */
@Service(value = "com.github.hualuomoli.mvc.security.DefaultAuth")
public class DefaultAuth extends AuthAbstract {

	@Override
	public Cache getCache() {
		if (super.getCache() == null) {
			super.setCache(new Cache() {

				private final HashMap<String, Serializable> maps = Maps.newHashMap();

				@Override
				public boolean set(String key, Serializable serializable, int expire) {
					maps.put(key, serializable);
					return true;
				}

				@Override
				public boolean set(String key, Serializable serializable) {
					maps.put(key, serializable);
					return true;
				}

				@Override
				public boolean remove(String key) {
					maps.remove(key);
					return true;
				}

				@Override
				public long plus(String key, long number) {
					long data = 0;
					if (this.exists(key)) {
						data = Long.parseLong(String.valueOf(maps.get(key)));
					}
					maps.put(key, ++data);
					return data;
				}

				@Override
				public boolean isInstance() {
					return true;
				}

				@SuppressWarnings("unchecked")
				@Override
				public <T> T get(String key) {
					return (T) maps.get(key);
				}

				@Override
				public boolean exists(String key) {
					return maps.containsKey(key);
				}

				@Override
				public void empty() {
					maps.clear();
				}
			});
		}
		return super.getCache();
	}

	@Override
	public String[] getRoles(String username) {
		return new String[] {};
	}

	@Override
	public String[] getPermissions(String uesrname) {
		return new String[] {};
	}

}
