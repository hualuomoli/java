package com.github.hualuomoli.demosecurity;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.security.Security;
import com.github.hualuomoli.security.entity.User;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service(value = "com.github.hualuomoli.demosecurity.DemoSecurity")
public class DemoSecurity implements Security {

	private static final Map<String, User> userMap = Maps.newHashMap();
	private static final Map<String, Set<String>> roleMap = Maps.newHashMap();
	private static final Map<String, Set<String>> permissionMap = Maps.newHashMap();

	@Override
	public boolean login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String token = user.getToken();

		boolean success = StringUtils.equals(username, User.DEFAULT_USERNAME) && StringUtils.equals(password, User.DEFAULT_PASSWORD);

		if (success) {

			// cache login user
			userMap.put(token, user);

			// cache user role
			roleMap.put(token, this.getRoles(username));

			// cache user permission
			permissionMap.put(token, this.getPermissions(username));

		}
		return success;
	}

	@Override
	public boolean isLogin(String token) {
		if (StringUtils.isEmpty(token)) {
			return false;
		}
		return userMap.containsKey(token);
	}

	@Override
	public void logout(String token) {

		if (StringUtils.isEmpty(token)) {
			return;
		}

		User user = userMap.get(token);
		if (user == null) {
			return;
		}
		userMap.remove(token);
	}

	@Override
	public Set<String> getRoles(String username) {
		return Sets.newHashSet("manager");
	}

	@Override
	public boolean hasRole(String username, String role) {
		return true;
	}

	@Override
	public Set<String> getPermissions(String username) {
		return Sets.newHashSet("admin");
	}

	@Override
	public boolean hasPermission(String username, String permission) {
		return true;
	}

}
