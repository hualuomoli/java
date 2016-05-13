package com.github.hualuomoli.demo.entity;

import java.util.Date;
import java.util.List;

import com.github.hualuomoli.base.annotation.EntityColumn;
import com.github.hualuomoli.base.annotation.EntityColumnType;
import com.github.hualuomoli.base.annotation.EntityIgnore;
import com.github.hualuomoli.base.annotation.EntityTable;

@EntityTable(name = "t_demo", comment = "测试demo")
public class Demo {

	@EntityColumn(name = "id", type = EntityColumnType.STRING, comment = "ID")
	private String id;
	private String name;
	@EntityColumn(type = EntityColumnType.CHAR, length = 1)
	private String sex;
	@EntityColumn(precision = 8, scale = 3, comment = "工资")
	private Double salary;
	@EntityColumn(precision = 3)
	private Integer age;
	@EntityColumn(type = EntityColumnType.DATE, comment = "工资")
	private Date birthDay;
	@EntityColumn(type = EntityColumnType.CLOB)
	private String remarks;
	@EntityIgnore // 忽略
	private String orderByStr; // 排序
	@EntityColumn(comment = "用户")
	private User user;
	// 不会使用
	private List<Address> address;

	public Demo() {
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrderByStr() {
		return orderByStr;
	}

	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

}
