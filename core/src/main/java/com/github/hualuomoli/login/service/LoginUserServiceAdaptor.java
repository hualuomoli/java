package com.github.hualuomoli.login.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.ServletUtils;
import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.plugin.cache.SerializeCache;

/**
 * 基本实现
 * @author hualuomoli
 *
 */
public abstract class LoginUserServiceAdaptor implements LoginUserService {

	private static final String PREFIX_TOKEN = "_token_";
	private static final String TOKEN_NAME = "token";

	// 缓存
	protected abstract SerializeCache getCache();

	// 获取token
	protected String getToken() {
		String token = null;
		HttpServletRequest req = ServletUtils.getRequest();
		// get from request
		token = req.getParameter(TOKEN_NAME);

		if (StringUtils.isBlank(token)) {
			// get from token
			token = req.getHeader(TOKEN_NAME);
		}
		if (StringUtils.isBlank(token)) {
			// get from cookie
			Cookie[] cookies = req.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equals(cookie.getName(), TOKEN_NAME)) {
						token = cookie.getValue();
						break;
					}
				}
			}
		}
		return token;
	}

	// 默认的用户名
	protected String getDefaultUsername() {
		return "SYSTEM_MANAGER";
	}

	@Override
	public String getUsername() {
		// 未登录
		String token = this.getToken();
		if (StringUtils.isBlank(token)) {
			return this.getDefaultUsername();
		}
		// 登陆超时
		String username = this.getCache().getSerializable(PREFIX_TOKEN + token);
		if (StringUtils.isBlank(username)) {
			return this.getDefaultUsername();
		}

		return username;
	}

	@Override
	public void login(String token, String username) {
		this.getCache().setSerializable(PREFIX_TOKEN + token, username);
	}

	@Override
	public void refresh() {
		// 未登录
		String token = this.getToken();
		if (StringUtils.isBlank(token)) {
			throw AuthException.NO_LOGIN;
		}
		this.getCache().getSerializableAndRefresh(PREFIX_TOKEN + token);
	}

	@Override
	public void logout() {
		String token = this.getToken();
		this.getCache().remove(PREFIX_TOKEN + token);
	}

	@Override
	public Date getCurrentDate() {
		return new Date();
	}

}
