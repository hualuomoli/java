package com.github.hualuomoli.mvc.security;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.hualuomoli.mvc.security.entity.User;
import com.github.hualuomoli.mvc.security.exception.AuthException;
import com.google.common.collect.Maps;

/**
 * 默认权限验证
 * @author hualuomoli
 *
 */
@Service(value = "com.github.hualuomoli.mvc.security.DefaultAuth")
public class DefaultAuth implements Auth {

	private static final Map<String, User> userMap = Maps.newHashMap();

	private String getToken() {
		return UUID.randomUUID().toString().replaceAll("[-]", "");
	}

	@Override
	public User login(User user) throws AuthException {

		user.setToken(this.getToken());

		// set to cache
		userMap.put(user.getToken(), user);

		return user;
	}

	@Override
	public void logout(User user) throws AuthException {
		userMap.remove(user.getToken());
	}

	@Override
	public boolean isLogin(User user) throws AuthException {
		return userMap.containsKey(user.getToken());
	}

	@Override
	public boolean hasRole(User user, String role) throws AuthException {
		return true;
	}

	@Override
	public boolean hasPermission(User user, String permission) throws AuthException {
		return true;
	}

}
