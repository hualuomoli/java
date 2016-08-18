package com.github.hualuomoli.extend.login.service;

import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.extend.entity.RoleMenu;
import com.github.hualuomoli.extend.entity.UserRole;
import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;
import com.github.hualuomoli.extend.user.service.UserService;
import com.github.hualuomoli.login.service.LoginUserService;
import com.github.hualuomoli.plugin.cache.SerializeCache;

@SuppressWarnings("rawtypes")
@Service(value = "com.github.hualuomoli.extend.login.service.LoginUserServiceAdaptor")
public abstract class LoginUserServiceAdaptor implements LoginUserService, Notifer {

	private static final String PREFIX_TOKEN = "_token_";
	private static final String PREFIX_ROLE = "_role_";
	private static final String PREFIX_PERMISSION = "_permission_";

	@Autowired
	private UserService userService;

	// 缓存
	protected abstract SerializeCache getCache();

	// 获取token
	protected abstract String getToken();

	@Override
	public String getUsername() {
		try {
			return this.getLoginUsername();
		} catch (Exception e) {
		}
		return "system";
	}

	@Override
	public String getLoginUsername() {
		// 未登录
		String token = this.getToken();
		if (StringUtils.isBlank(token)) {
			throw AuthException.NO_LOGIN;
		}
		// 登陆超时
		String username = this.getCache().getSerializableAndRefresh(PREFIX_TOKEN + token);
		if (StringUtils.isBlank(username)) {
			throw AuthException.OVER_TIME;
		}

		return username;
	}

	@Override
	public HashSet<String> getLoginUserRoles() {
		String username = this.getLoginUsername();
		HashSet<String> roles = this.getCache().getSerializable(PREFIX_ROLE + username);
		if (roles == null || roles.size() == 0) {
			roles = userService.getUserRoles(username);
			if (roles != null && roles.size() > 0) {
				this.getCache().setSerializable(PREFIX_ROLE + username, roles);
			}
		}
		return roles;
	}

	@Override
	public HashSet<String> getLoginUserPermissions() {
		String username = this.getLoginUsername();
		HashSet<String> permissions = this.getCache().getSerializable(PREFIX_PERMISSION + username);
		if (permissions == null || permissions.size() == 0) {
			permissions = userService.getUserPermission(username);
			if (permissions != null && permissions.size() > 0) {
				this.getCache().setSerializable(PREFIX_PERMISSION + username, permissions);
			}
		}
		return permissions;
	}

	@Override
	public void setUsername(String token, String username) {
		this.getCache().setSerializable(PREFIX_TOKEN + token, username);
	}

	@Override
	public void refreshUsername() {
		this.getLoginUsername();
	}

	@Override
	public Date getCurrentDate() {
		return new Date();
	}

	@Override
	public boolean support(Class cls) {
		if (cls == UserRole.class || cls == RoleMenu.class) {
			return true;
		}
		return false;
	}

	@Override
	public void notice(Noticer noticer) {
		if (noticer instanceof UserRole) {
			// menu
			UserRole userRole = (UserRole) noticer;
			String usrname = userRole.getUsername();
			// remove
			this.getCache().remove(PREFIX_ROLE + usrname);
			this.getCache().remove(PREFIX_PERMISSION + usrname);
		} else if (noticer instanceof RoleMenu) {
			// menu
			UserRole userRole = (UserRole) noticer;
			String usrname = userRole.getUsername();
			// remove
			this.getCache().remove(PREFIX_PERMISSION + usrname);
		}

	}

}
