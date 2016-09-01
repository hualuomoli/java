package com.github.hualuomoli.plugin.roll.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BasePollData extends com.github.hualuomoli.plugin.roll.entity.PollData {

	/** 数据ID - 查询数组 */
	private java.lang.String[] dataIdArray;
	
	public BasePollData(){
	}
	
	
	public java.lang.String[] getDataIdArray() {
		return dataIdArray;
	}
	
	public void setDataIdArray(java.lang.String[] dataIdArray) {
		if (dataIdArray == null || dataIdArray.length == 0) {
			return;
		}
		this.dataIdArray = dataIdArray;
	}

}
