package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.entity.CommonField;

/**
 * 树
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
public class TreeField extends CommonField {

	@EntityColumn(comment = "父编码")
	private String parentCode;
	@EntityColumn(comment = "排序,值越小越靠前,从1开始")
	private Integer dataSort;
	@EntityColumn(comment = "级别,从1开始")
	private Integer dataLevel;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getDataSort() {
		return dataSort;
	}

	public void setDataSort(Integer dataSort) {
		this.dataSort = dataSort;
	}

	public Integer getDataLevel() {
		return dataLevel;
	}

	public void setDataLevel(Integer dataLevel) {
		this.dataLevel = dataLevel;
	}

}
