package com.github.hualuomoli.demo.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseRole extends com.github.hualuomoli.demo.extend.entity.Role
 implements com.github.hualuomoli.base.BasePersistent
 {

	
	public BaseRole(){
	}
	
	public BaseRole(
		java.lang.String roleCode
	){
		this.setRoleCode(roleCode);
	}
	

}
