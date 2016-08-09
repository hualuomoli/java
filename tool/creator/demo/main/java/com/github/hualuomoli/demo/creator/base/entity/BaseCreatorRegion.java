package com.github.hualuomoli.demo.creator.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseCreatorRegion extends com.github.hualuomoli.demo.creator.entity.CreatorRegion
 implements com.github.hualuomoli.base.BasePersistent
 {

	
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
