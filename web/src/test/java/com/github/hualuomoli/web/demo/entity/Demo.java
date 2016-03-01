package com.github.hualuomoli.web.demo.entity;

import com.github.hualuomoli.base.entity.BaseEntity;

public class Demo extends BaseEntity {

	private String name;
	private String sex;
	private Integer age;
	private String address;

	public Demo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
