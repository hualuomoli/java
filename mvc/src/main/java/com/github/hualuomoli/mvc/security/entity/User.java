package com.github.hualuomoli.mvc.security.entity;

/**
 * 权限用户
 * @author hualuomoli
 *
 */
public class User {

	public static final String USERNAME = "username";
	public static final String TOKEN = "token";

	private String username;
	private String token;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
