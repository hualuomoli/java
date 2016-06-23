package com.github.hualuomoli.tool.raml.entity;

import java.util.List;

import com.google.common.collect.Lists;

// JSON参数
public class RamlJsonParam extends RamlParam {

	private static final long serialVersionUID = 5841635134050608464L;

	private String className; // 类名
	private List<RamlJsonParam> children = Lists.newArrayList();

	public RamlJsonParam() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<RamlJsonParam> getChildren() {
		return children;
	}

	public void setChildren(List<RamlJsonParam> children) {
		this.children = children;
	}

}
