package com.github.hualuomoli.demo.creator.base.entity;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.Persistent;

public class BaseRegion extends com.github.hualuomoli.demo.entity.Region implements Persistent {

	
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
