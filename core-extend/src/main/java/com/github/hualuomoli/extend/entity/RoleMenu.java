package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

@SuppressWarnings("serial")
@EntityTable(comment = "角色具有的菜单和权限")
public class RoleMenu extends BaseField {

	@EntityColumn(name = "role_code", comment = "角色编码")
	@EntityColumnQuery(inArray = true)
	private String roleCode;
	@EntityColumn(name = "menu_code", comment = "菜单编码")
	@EntityColumnQuery(inArray = true)
	private String menuCode;

	public RoleMenu() {
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
