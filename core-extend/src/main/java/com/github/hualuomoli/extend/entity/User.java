package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

@SuppressWarnings("serial")
@EntityTable(comment = "登录用户", unique = { "username" })
public class User extends BaseField {

	@EntityColumn(comment = "用户名", length = 32)
	private String username;
	@EntityColumn(comment = "用户手机号", length = 20)
	private String phone;
	@EntityColumn(comment = "用户邮箱", length = 32)
	private String email;
	@EntityColumn(comment = "用户密码", length = 64)
	private String password;

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

}
