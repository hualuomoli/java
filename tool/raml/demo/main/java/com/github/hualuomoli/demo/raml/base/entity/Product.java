package com.github.hualuomoli.demo.raml.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.EntityColumn;
import com.github.hualuomoli.base.annotation.EntityIgnore;
import com.github.hualuomoli.base.annotation.EntityTable;
import com.github.hualuomoli.base.annotation.EntityColumnType;

/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:03
 * @Version 1.0
 */
@EntityTable
public class Product {

	@EntityColumn(comment = "商品号")
	private String id;
	@EntityColumn(comment = "商品名")
	private String name;


	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}



}
