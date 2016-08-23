package com.github.hualuomoli.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseUser extends com.github.hualuomoli.extend.entity.User
 implements com.github.hualuomoli.base.persistent.BasePersistent
 {

	
	public BaseUser(){
	}
	
	public BaseUser(
		java.lang.String username
	){
		this.setUsername(username);
	}
	

}
