package com.github.hualuomoli.plugins.cache.memcache;

import java.io.Serializable;

public class Bean implements Serializable {

	private static final long serialVersionUID = -1983484248598408704L;

	private String name;

	private int age;

	private int index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		String bean = "{name:" + this.getName() + ",age:" + this.getAge() + ",index:" + this.getIndex() + "}";
		return bean;
	}

}
