package com.github.hualuomoli.base.entity;

import java.io.Serializable;

import org.apache.ibatis.type.JdbcType;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;

// 公共属性
@SuppressWarnings("serial")
public abstract class BaseField implements Serializable {

	@EntityColumn(name = "id", type = JdbcType.VARCHAR, comment = "主键", length = 32)
	private String id;

	public BaseField() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
