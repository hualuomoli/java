package com.github.hualuomoli.demo.creator.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

@SuppressWarnings("serial")
@EntityTable(name = "t_creator_user")
public class CreatorUser extends CommonField {

	private String id;
	@EntityColumn(length = 20)
	private String username;
	private String nickname;
	private Integer number;

	public CreatorUser() {
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
