package com.github.hualuomoli.base.entity;

import java.io.Serializable;

import com.github.hualuomoli.base.BasePersistent;
import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

// 公共属性
@SuppressWarnings("serial")
public abstract class BaseField implements Serializable, BasePersistent {

	@EntityColumn(name = "id", type = EntityColumnType.STRING, comment = "主键", length = 32)
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
