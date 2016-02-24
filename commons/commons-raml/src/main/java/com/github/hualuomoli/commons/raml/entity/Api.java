package com.github.hualuomoli.commons.raml.entity;

import java.util.List;

public class Api {

	private String packageName;
	private String moduleName;
	private String className;
	private String modulePath;

	private List<ApiResource> resourceList;

	public Api() {
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<ApiResource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ApiResource> resourceList) {
		this.resourceList = resourceList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getModulePath() {
		return modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

}
