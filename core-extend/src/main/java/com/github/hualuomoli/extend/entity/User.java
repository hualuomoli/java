package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

@SuppressWarnings("serial")
@EntityTable(comment = "登录用户", unique = { "username" })
public class User extends CommonField {

	@EntityColumn(comment = "用户名", length = 32)
	private String username;
	@EntityColumn(comment = "用户手机号", length = 20)
	private String phone;
	@EntityColumn(comment = "用户邮箱", length = 32)
	private String email;
	@EntityColumn(comment = "用户密码", length = 64)
	private String password;
	@EntityColumn(comment = "登录用户类型", length = 3)
	private Integer type;
	@EntityColumn(comment = "登录用户类型名称", length = 32)
	private String typeName;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
