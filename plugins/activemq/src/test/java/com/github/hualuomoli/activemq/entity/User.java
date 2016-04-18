package com.github.hualuomoli.activemq.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User implements Serializable {

	private static final long serialVersionUID = 500305649459551962L;

	private String id;
	private String username;
	private String password;
	private String nickname;

	public User() {
	}

	public User(String id, String username, String password, String nickname) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
