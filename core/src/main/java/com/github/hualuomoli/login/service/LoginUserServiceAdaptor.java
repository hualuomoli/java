package com.github.hualuomoli.login.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.plugin.cache.SerializeCache;

/**
 * 基本实现
 * @author hualuomoli
 *
 */
public abstract class LoginUserServiceAdaptor implements LoginUserService {

	private static final String PREFIX_TOKEN = "_token_";

	// 缓存
	protected abstract SerializeCache getCache();

	// 获取token
	protected abstract String getToken();

	@Override
	public String getUsername() {
		// 未登录
		String token = this.getToken();
		if (StringUtils.isBlank(token)) {
			throw AuthException.NO_LOGIN;
		}
		// 登陆超时
		String username = this.getCache().getSerializable(PREFIX_TOKEN + token);
		if (StringUtils.isBlank(username)) {
			throw AuthException.OVER_TIME;
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
