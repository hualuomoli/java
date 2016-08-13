package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;
import com.github.hualuomoli.extend.notice.Noticer;

/**
 * 角色
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "角色", unique = { "roleCode" })
public class Role extends BaseField implements Noticer {

	@EntityColumn(comment = "角色编码")
	@EntityColumnQuery(inArray = true)
	private String roleCode;
	@EntityColumn(comment = "角色名称")
	private String roleName;

	public Role() {
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
