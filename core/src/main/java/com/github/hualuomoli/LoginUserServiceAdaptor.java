package com.github.hualuomoli;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.commons.util.ServletUtils;
import com.github.hualuomoli.plugin.cache.DefaultSerializeCache;

// token -> username -> user
@Service(value = "com.github.hualuomoli.LoginUserServiceAdaptor")
public class LoginUserServiceAdaptor implements LoginUserService {

	public static final String NULL_TOKEN = "0123456789abcdefghijklmnopqrstuvwxyz";
	public static final String NULL_USERNAME = "admin";

	@Autowired
	private DefaultSerializeCache cache;

	public String getToken() {
		HttpServletRequest req = ServletUtils.getRequest();
		if (req == null) {
			return NULL_TOKEN;
		}
		String token = null;
		if (StringUtils.isNotBlank(token = ServletUtils.getParameter(TOKEN_NAME))) {
			return token;
		}
		if (StringUtils.isNotBlank(token = ServletUtils.getHeader(TOKEN_NAME))) {
			return token;
		}
		if (StringUtils.isNotBlank(token = ServletUtils.getCookie(TOKEN_NAME))) {
			return token;
		}
		return null;
	}

	@Override
	public String getUsername() {
		String token = this.getToken();
		// 没有token
		if (token == null) {
			throw LoginUserSecurityException.NO_TOKEN;
		}
		// 没有request，如JUNIT测试
		if (StringUtils.equals(NULL_TOKEN, this.getToken())) {
			return NULL_USERNAME;
		}
		String username = cache.getSerializable(token);
		if (StringUtils.isBlank(username)) {
			throw LoginUserSecurityException.TOKEN_OVER_TIME;
		}
		return username;
	}

	@Override
	public <T extends Serializable> LoginUser<T> getLoginUser() {
		String username = this.getUsername();
		if (StringUtils.equals(username, NULL_USERNAME)) {
			LoginUser<T> loginUser = new LoginUser<T>();
			loginUser.setUsername(username);
			return loginUser;
		}
		return cache.getSerializable(username);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T getUser() {
		return (T) this.getLoginUser().getObj();
	}

	@Override
	public <T extends Serializable> void setUser(String username, T obj) {
		String token = this.getToken();
		// 没有token
		if (token == null) {
			throw LoginUserSecurityException.NO_TOKEN;
		}
		cache.set(token, username);
		cache.set(username, obj);
	}

	@Override
	public Date getCurrentDate() {
		return new Date();
	}

}
