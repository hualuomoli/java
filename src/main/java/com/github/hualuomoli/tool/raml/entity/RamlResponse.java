package com.github.hualuomoli.tool.raml.entity;

import java.io.Serializable;
import java.util.List;

import com.github.hualuomoli.tool.raml.JavaParser;
import com.google.common.collect.Lists;

/**
 * 相应(只是内部数据)
 * @author hualuomoli
 *
 */
public class RamlResponse implements Serializable {

	private static final long serialVersionUID = 8368737077707516558L;

	private String className; // 请求实体类名称
	private List<RamlJsonParam> jsonParams = Lists.newArrayList(); // 参数
	private JavaParser.JavaTool.Json json;

	public RamlResponse() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<RamlJsonParam> getJsonParams() {
		return jsonParams;
	}

	public void setJsonParams(List<RamlJsonParam> jsonParams) {
		this.jsonParams = jsonParams;
	}

	public JavaParser.JavaTool.Json getJson() {
		return json;
	}

	public void setJson(JavaParser.JavaTool.Json json) {
		this.json = json;
	}

}
