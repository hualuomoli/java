package com.github.hualuomoli.demo.raml.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityIgnore;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:46
 * @Version 1.0
 */
@EntityTable
public class Order {

	@EntityColumn(comment = "订单日期")
	private Date date;
	@EntityColumn(comment = "订单号")
	private String id;

	private Product products;

	public Date getDate(){
		return date;
	}

	public void setDate(Date date){
		this.date = date;
	}
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public Product getProducts(){
		return products;
	}

	public void setProducts(Product products){
		this.products = products;
	}


}
