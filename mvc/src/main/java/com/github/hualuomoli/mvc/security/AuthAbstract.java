package com.github.hualuomoli.mvc.security;

import java.util.HashSet;
import java.util.UUID;

import com.github.hualuomoli.plugin.cache.Cache;
import com.google.common.collect.Sets;

/**
 * 使用缓存cache存储权限数据
 * @author hualuomoli
 *
 */
public abstract class AuthAbstract implements Auth {

	public static final String PREFIX_USER = "_u_";
	public static final String PREFIX_ROLE = "_r_";
	public static final String PREFIX_PERMISSION = "_p_";

	private Cache cache;

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	// get unique data
	protected String getUnique() {
		return UUID.randomUUID().toString().replaceAll("[-]", "");
	}

	@Override
	public int getDataExpire() {
		return DEFAULT_EXPIRE;
	}

	@Override
	public String login(String username) {
		String token = getUnique();
		login(username, token);
		return token;
	}

	@Override
	public void login(String username, String token) {

		String key = PREFIX_USER + username;

		// set token --> username
		this.getCache().set(token, key, this.getDataExpire());

		// set username --> tokens
		HashSet<String> tokens = Sets.newHashSet();
		if (this.getCache().exists(key)) {
			HashSet<String> ts = this.getCache().get(key);
			for (String t : ts) {
				if (this.getCache().exists(t)) {
					tokens.add(t);
				}
			}
		}
		tokens.add(token);
		this.getCache().set(key, tokens, this.getDataExpire());

		this.refresh(username);

	}

	@Override
	public void logout(String token) {
		this.getCache().remove(token);
	}

	@Override
	public boolean isLogin(String token) {
		return this.getCache().exists(token);
	}

	@Override
	public String[] logged(String username) {
		String key = PREFIX_USER + username;
		HashSet<String> tokens = this.getCache().get(key);
		return tokens.toArray(new String[] {});
	}

	@Override
	public boolean hasRole(String username, String role) {
		String key = PREFIX_ROLE + username;
		HashSet<String> roles = this.getCache().get(key);
		return roles.contains(role);
	}

	@Override
	public boolean hasPermission(String username, String permission) {
		String key = PREFIX_PERMISSION + username;
		HashSet<String> permissions = this.getCache().get(key);
		return permissions.contains(permission);
	}

	// refresh user
	public void refresh(String username) {
		String key = null;

		// role
		key = PREFIX_ROLE + username;
		this.getCache().set(key, this.getRoles(username));

		// permission
		key = PREFIX_PERMISSION + username;
		this.getCache().set(key, this.getPermissions(username));
	}

	// get user roles
	public abstract String[] getRoles(String username);

	// get user permissions
	public abstract String[] getPermissions(String uesrname);

}
