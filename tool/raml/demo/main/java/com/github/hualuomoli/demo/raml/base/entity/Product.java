package com.github.hualuomoli.demo.raml.base.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityTable;

/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@EntityTable(comment = "")
public class Product {

	@EntityColumn(comment = "商品号")
	private String id;
	@EntityColumn(comment = "商品名")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
