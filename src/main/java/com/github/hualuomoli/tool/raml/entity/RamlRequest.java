package com.github.hualuomoli.tool.raml.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 请求
 * @author hualuomoli
 *
 */
public class RamlRequest implements Serializable {

	private static final long serialVersionUID = -3095507215733959923L;

	private String className; // 请求实体类名称
	private List<RamlParam> params = Lists.newArrayList(); // 参数
	private List<RamlJsonParam> jsonParams = Lists.newArrayList(); // 参数

	public RamlRequest() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<RamlParam> getParams() {
		return params;
	}

	public void setParams(List<RamlParam> params) {
		this.params = params;
	}

	public List<RamlJsonParam> getJsonParams() {
		return jsonParams;
	}

	public void setJsonParams(List<RamlJsonParam> jsonParams) {
		this.jsonParams = jsonParams;
	}

}
