package com.github.hualuomoli.system.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseUser extends com.github.hualuomoli.system.entity.User
 implements com.github.hualuomoli.base.BasePersistent, com.github.hualuomoli.base.CommonPersistent
 {

	
	public BaseUser(){
	}
	
	public BaseUser(
		java.lang.String username
	){
		this.setUsername(username);
	}
	

}
