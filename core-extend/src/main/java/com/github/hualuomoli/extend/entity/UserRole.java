package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

/**
 * 用户拥有的角色
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "用户与角色的关系")
public class UserRole extends BaseField {

	@EntityColumn(name = "username", comment = "用户名")
	private String username;
	@EntityColumn(name = "role_code", comment = "角色编码")
	private String roleCode;

	public UserRole() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
