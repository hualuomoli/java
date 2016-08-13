package com.github.hualuomoli.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseRole extends com.github.hualuomoli.extend.entity.Role
 implements com.github.hualuomoli.base.BasePersistent
 {

	/** 角色编码 - 查询数组 */
	private java.lang.String[] roleCodeArray;
	
	public BaseRole(){
	}
	
	public BaseRole(
		java.lang.String roleCode
	){
		this.setRoleCode(roleCode);
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
