package com.github.hualuomoli.system.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

/**
 * 用户
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "用户", unique = { "username" })
public class User extends CommonField {

	@EntityColumn(comment = "用户名")
	private String username; // 用户名默认使用id
	@EntityColumn(comment = "手机号码")
	private String phone;
	@EntityColumn(comment = "邮箱")
	private String email;
	@EntityColumn(comment = "密码,使用Digist加密")
	private String password;
	@EntityColumn(comment = "昵称")
	private String nickname;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
