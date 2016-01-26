package com.github.hualuomoli.commons.json;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Demo {

	private Integer id;
	private String name;
	private List<String> list;
	private Map<String, String> map;
	private List<Demo> demoList;
	private Map<String, Demo> demoMap;

	public Demo() {
	}

	public Demo(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<Demo> getDemoList() {
		return demoList;
	}

	public void setDemoList(List<Demo> demoList) {
		this.demoList = demoList;
	}

	public Map<String, Demo> getDemoMap() {
		return demoMap;
	}

	public void setDemoMap(Map<String, Demo> demoMap) {
		this.demoMap = demoMap;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}