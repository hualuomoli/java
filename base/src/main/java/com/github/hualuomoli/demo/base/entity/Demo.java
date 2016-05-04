package com.github.hualuomoli.demo.base.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.Comment;
import com.github.hualuomoli.base.annotation.DBColumn;
import com.github.hualuomoli.base.entity.BaseEntity;

@Comment("例子")
public class Demo extends BaseEntity {

	@Comment("名称")
	@DBColumn(true)
	private String name;

	@Comment("年龄")
	@DBColumn(precision = 3)
	private Integer age;

	@Comment("测试字段")
	private Long seconds;

	@Comment("工资")
	@DBColumn(precision = 7, scale = 3)
	private Double salary;

	@Comment("性别")
	@DBColumn(value = true, defaultValue = "1")
	private Boolean sex;

	@Comment("生日")
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

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
