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
public class Address {

	@EntityColumn(comment = "城市")
	private String city;
	@EntityColumn(comment = "区县")
	private String couty;
	@EntityColumn(comment = "具体地址")
	private String name;
	@EntityColumn(comment = "联系电话")
	private String[] phones;
	@EntityColumn(comment = "省份")
	private String province;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCouty() {
		return couty;
	}

	public void setCouty(String couty) {
		this.couty = couty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getPhones() {
		return phones;
	}

	public void setPhones(String[] phones) {
		this.phones = phones;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
