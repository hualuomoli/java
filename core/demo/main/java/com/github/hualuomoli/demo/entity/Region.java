package com.github.hualuomoli.demo.entity;

import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

/**
 * 区域
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "地区", unique = { "code", "type" })
public class Region extends BaseField {

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
