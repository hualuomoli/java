package com.github.hualuomoli.demo.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseRegion extends com.github.hualuomoli.demo.entity.Region {

	
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
