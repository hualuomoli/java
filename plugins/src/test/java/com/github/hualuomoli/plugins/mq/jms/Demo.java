package com.github.hualuomoli.plugins.mq.jms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Demo implements Serializable {

	private static final long serialVersionUID = -7424771052776981520L;

	private String id;
	private String name;

	private List<Demo> child;

	private Map<String, Demo> map;

	public Demo() {
	}

	public Demo(String id, String name) {
		this.id = id;
		this.name = name;
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

	public List<Demo> getChild() {
		return child;
	}

	public void setChild(List<Demo> child) {
		this.child = child;
	}

	public Map<String, Demo> getMap() {
		return map;
	}

	public void setMap(Map<String, Demo> map) {
		this.map = map;
	}

}
