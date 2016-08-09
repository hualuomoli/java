package com.github.hualuomoli.demo.creator.entity;

import java.util.Date;
import java.util.List;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

@SuppressWarnings("serial")
@EntityTable(name = "t_creator_demo", comment = "测试demo", unique = { "name" })
public class CreatorDemo extends CommonField {

	@EntityColumnQuery(leftLike = true, rightLike = true, bothLike = true, inArray = true)
	private String name;
	@EntityColumn(comment = "性别", type = EntityColumnType.CHAR, length = 1)
	private String sex;
	@EntityColumn(precision = 8, scale = 3, comment = "工资")
	@EntityColumnQuery(greaterThan = true, greaterEqual = true, lessThan = true, lessEqual = true, inArray = true)
	private Double salary;
	@EntityColumn(precision = 3, defaultValue = "20", comment = "年龄")
	@EntityColumnQuery(greaterThan = true, greaterEqual = true, lessThan = true, lessEqual = true, inArray = true)
	private Integer age;
	@EntityColumn(type = EntityColumnType.DATE, comment = "生日")
	@EntityColumnQuery(greaterThan = true, greaterEqual = true, lessThan = true, lessEqual = true)
	private Date birthDay;
	@EntityColumn(comment = "备注", type = EntityColumnType.CLOB)
	private String remarks;
	@EntityColumn(comment = "用户", relation = "username")
	@EntityColumnQuery(inArray = true)
	private CreatorUser user;
	private CreatorRegion region;
	// 不会使用
	private List<CreatorAddress> address;

	public CreatorDemo() {
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

	public CreatorUser getUser() {
		return user;
	}

	public void setUser(CreatorUser user) {
		this.user = user;
	}

	public CreatorRegion getRegion() {
		return region;
	}

	public void setRegion(CreatorRegion region) {
		this.region = region;
	}

	public List<CreatorAddress> getAddress() {
		return address;
	}

	public void setAddress(List<CreatorAddress> address) {
		this.address = address;
	}

}
