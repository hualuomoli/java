package com.github.hualuomoli.plugin.roll.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BasePollSuccessHis extends com.github.hualuomoli.plugin.roll.entity.PollSuccessHis {

	/** 数据ID - 查询数组 */
	private java.lang.String[] dataIdArray;
	
	public BasePollSuccessHis(){
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
