package com.github.hualuomoli.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseRoleMenu extends com.github.hualuomoli.extend.entity.RoleMenu
 implements com.github.hualuomoli.base.persistent.BasePersistent
 {

	/** 角色编码 - 查询数组 */
	private java.lang.String[] roleCodeArray;
	
	public BaseRoleMenu(){
	}
	
	
	public java.lang.String[] getRoleCodeArray() {
		return roleCodeArray;
	}
	
	public void setRoleCodeArray(java.lang.String[] roleCodeArray) {
		if (roleCodeArray == null || roleCodeArray.length == 0) {
			return;
		}
		this.roleCodeArray = roleCodeArray;
	}

}
