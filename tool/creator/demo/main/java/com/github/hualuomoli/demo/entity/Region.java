package com.github.hualuomoli.demo.entity;

import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.annotation.entity.EntityUnique;
import com.github.hualuomoli.base.entity.CommonField;

/**
 * 区域
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable
@EntityUnique(union = true, columnNmaes = { "code", "type" })
public class Region extends CommonField {

	private String code; // 编码
	private String name; // 名称
	private Integer type; // 类型

	public Region() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
