package com.github.hualuomoli.security.entity;

/**
 * 权限用户
 * @author hualuomoli
 *
 */
public class User {

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String TOKEN = "token";

	// 默认值
	public static final String DEFAULT_USERNAME = "admin";
	public static final String DEFAULT_PASSWORD = "admin";

	private String username;
	private String password;
	private String token;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
