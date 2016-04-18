package com.github.hualuomoli.demosecurity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.security.Security;
import com.google.common.collect.Maps;

@Service(value = "com.github.hualuomoli.demosecurity.DemoSecurity")
public class DemoSecurity implements Security {

	private static final Map<String, String> userMap = Maps.newHashMap();
	private static final Map<String, Set<String>> roleMap = Maps.newHashMap();
	private static final Map<String, Set<String>> permissionMap = Maps.newHashMap();

	@Override
	public String login(String username) {
		String token = UUID.randomUUID().toString().replaceAll("[-]", "");
		userMap.put(token, username);
		return token;
	}

	@Override
	public boolean isLogin(String token) {
		return userMap.containsKey(token);
	}

	@Override
	public boolean isLogin(String username, String token) {
		return StringUtils.equals(username, userMap.get(token));
	}

	@Override
	public void logout(String token) {
		userMap.remove(token);
	}

	@Override
	public void addRole(String username, Set<String> roles) {
		roleMap.put(username, roles);
	}

	@Override
	public boolean hasRole(String username, String role) {
		Set<String> roles = roleMap.get(username);
		if (roles == null || roles.isEmpty()) {
			return false;
		}
		return roles.contains(role);
	}

	@Override
	public void addPermisions(String username, Set<String> permissions) {
		permissionMap.put(username, permissions);
	}

	@Override
	public boolean hasPermission(String username, String permission) {
		Set<String> permissions = permissionMap.get(username);
		if (permissions == null || permissions.isEmpty()) {
			return false;
		}
		return permissions.contains(permission);
	}

}
