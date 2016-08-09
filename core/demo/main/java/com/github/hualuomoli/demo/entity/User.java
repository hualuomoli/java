package com.github.hualuomoli.demo.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

@SuppressWarnings("serial")
@EntityTable(comment = "用户", name = "t_user")
public class User extends CommonField {

	private String id;
	@EntityColumn(comment = "用户名", length = 20)
	private String username;
	private String nickname;
	private Integer number;

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
