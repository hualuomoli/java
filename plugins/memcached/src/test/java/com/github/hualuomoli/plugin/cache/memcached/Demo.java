package com.github.hualuomoli.plugin.cache.memcached;

import java.io.Serializable;
import java.util.Date;

public class Demo implements Serializable {

	private static final long serialVersionUID = -5645379351056121457L;

	private String name;
	private Integer age;
	private Long seconds;
	private Double salary;
	private String sex;
	private Date birthDay;

	public Demo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
