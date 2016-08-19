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

}
