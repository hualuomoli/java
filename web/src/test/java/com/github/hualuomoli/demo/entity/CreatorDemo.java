package com.github.hualuomoli.demo.entity;

import java.util.Date;

import com.github.hualuomoli.base.entity.BaseEntity;

public class CreatorDemo extends BaseEntity {

	private String name;
	private String sex;
	private Integer age;
	private Double salary;
	private Date birthDay;
	private Long seconds;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

}
