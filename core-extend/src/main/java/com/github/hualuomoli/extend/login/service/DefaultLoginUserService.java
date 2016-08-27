package com.github.hualuomoli.extend.login.service;

import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.commons.util.ServletUtils;
import com.github.hualuomoli.extend.entity.RoleMenu;
import com.github.hualuomoli.extend.entity.UserRole;
import com.github.hualuomoli.extend.notice.Noticer;
import com.github.hualuomoli.extend.notice.Notifer;
import com.github.hualuomoli.extend.user.service.UserService;
import com.github.hualuomoli.login.service.LoginUserService;
import com.github.hualuomoli.login.service.LoginUserServiceAdaptor;
import com.github.hualuomoli.plugin.cache.SerializeCache;
import com.github.hualuomoli.plugin.cache.SerializeCacheAdaptor;
import com.google.common.collect.Maps;

@SuppressWarnings("rawtypes")
@Service(value = "com.github.hualuomoli.extend.login.service.DefaultLoginUserService")
public class DefaultLoginUserService extends LoginUserServiceAdaptor implements LoginUserService, Notifer {

	private static final String PREFIX_ROLE = "_role_";
	private static final String PREFIX_PERMISSION = "_permission_";

	private SerializeCache cache = new DefaultSerializeCache();

	@Autowired
	private UserService userService;

	// 缓存
	protected SerializeCache getCache() {
		return cache;
	}

	// 获取token
	protected String getToken() {
		String name = "token";
		String token = null;
		HttpServletRequest req = ServletUtils.getRequest();
		// get from request
		token = req.getParameter(name);

		if (StringUtils.isBlank(token)) {
			// get from token
			token = req.getHeader(name);
		}
		if (StringUtils.isBlank(token)) {
			// get from cookie
			Cookie[] cookies = req.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equals(cookie.getName(), name)) {
						token = cookie.getValue();
						break;
					}
				}
			}
		}
		return token;
	}

	@Override
	public HashSet<String> getUserRoles() {
		String username = this.getUsername();
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
	public HashSet<String> getUserPermissions() {
		String username = this.getUsername();
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

	// 默认的实现
	private class DefaultSerializeCache extends SerializeCacheAdaptor implements SerializeCache {

		private Map<String, Object> dataMap = Maps.newHashMap();
		private Map<String, Long> expireMap = Maps.newHashMap();

		@Override
		public boolean success() {
			return true;
		}

		@Override
		public boolean setValue(String key, byte[] data, int expire) {
			// data
			dataMap.put(key, data);
			// expire
			expireMap.put(key, System.currentTimeMillis() + expire);

			return true;
		}

		@Override
		public byte[] getValue(String key) {
			// expire
			Long expire = expireMap.get(key);
			if (expire < System.currentTimeMillis()) {
				return null;
			}
			// data
			return (byte[]) dataMap.get(key);
		}

		@Override
		public boolean removeKey(String key) {
			// expire
			expireMap.remove(key);
			// data
			dataMap.remove(key);

			return true;
		}

		@Override
		public boolean keyExists(String key) {
			return expireMap.containsKey(key) && dataMap.containsKey(key) && expireMap.get(key) > System.currentTimeMillis();
		}

		@Override
		public Long plusNumber(String key, Long number) {
			Long n = (Long) dataMap.get(key);
			if (n == null) {
				n = 0L;
			}
			n += number;

			// set
			dataMap.put(key, n);

			return n;
		}

		@Override
		public void clear() {
			expireMap.clear();
			dataMap.clear();
		}

	}
}
