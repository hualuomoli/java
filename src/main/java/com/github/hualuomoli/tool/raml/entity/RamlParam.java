package com.github.hualuomoli.tool.raml.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

// 参数
public class RamlParam implements Serializable {

	private static final long serialVersionUID = -5593565515084901044L;

	private String name; // 名称
	private String type; // 数据类型
	private String comment; // 注释
	private String def; // 例子
	private List<String> annos = Lists.newArrayList(); // 注解

	public RamlParam() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public List<String> getAnnos() {
		return annos;
	}

	public void setAnnos(List<String> annos) {
		this.annos = annos;
	}

}
