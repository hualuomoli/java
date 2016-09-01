package com.github.hualuomoli.plugin.roll;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Demo implements Serializable {

	private Integer age;
	private Date date;

	public Demo(Integer age, Date date) {
		this.age = age;
		this.date = date;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
