package com.github.hualuomoli.web.login;

public class Login {

	private String username;
	private String password;

	public Login() {
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
