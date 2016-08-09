package com.github.hualuomoli.demo.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseRegion extends com.github.hualuomoli.demo.entity.Region
 implements com.github.hualuomoli.base.BasePersistent
 {

	
	public BaseRegion(){
	}
	
	public BaseRegion(
		java.lang.String code,
		java.lang.Integer type
	){
		this.setCode(code);
		this.setType(type);
	}
	

}
