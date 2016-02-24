package com.github.hualuomoli.commons.raml.entity;

import java.util.List;

public class ApiResource {

	private String uri;
	private String methodUriPath;
	private String methodName;
	private List<ApiAction> actionList;

	public ApiResource() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethodUriPath() {
		return methodUriPath;
	}

	public void setMethodUriPath(String methodUriPath) {
		this.methodUriPath = methodUriPath;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ApiAction> getActionList() {
		return actionList;
	}

	public void setActionList(List<ApiAction> actionList) {
		this.actionList = actionList;
	}

}
