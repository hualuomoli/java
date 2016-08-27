package com.github.hualuomoli.demo.creator.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseCreatorRegion extends com.github.hualuomoli.demo.creator.entity.CreatorRegion {

	
	public BaseCreatorRegion(){
	}
	
	public BaseCreatorRegion(
		java.lang.String code,
		java.lang.Integer type
	){
		this.setCode(code);
		this.setType(type);
	}
	

}
