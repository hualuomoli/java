package com.github.hualuomoli.tool.raml.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 方法定义
 * @author hualuomoli
 *
 */
public class RamlMethod implements Serializable {

	private static final long serialVersionUID = -281710995537850537L;

	private String resultType; // 返回类型String或者void
	private String methodName; // 方法名
	private RamlRequest request; // 请求实体类名称
	private RamlResponse response; // 响应实体类名称(如果不需要返回内容,该值为空)
	private List<RamlParam> uriParams; // URI参数
	private List<RamlParam> fileParams; // File参数

	public RamlMethod() {
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public RamlRequest getRequest() {
		return request;
	}

	public void setRequest(RamlRequest request) {
		this.request = request;
	}

	public RamlResponse getResponse() {
		return response;
	}

	public void setResponse(RamlResponse response) {
		this.response = response;
	}

	public List<RamlParam> getUriParams() {
		return uriParams;
	}

	public void setUriParams(List<RamlParam> uriParams) {
		this.uriParams = uriParams;
	}

	public List<RamlParam> getFileParams() {
		return fileParams;
	}

	public void setFileParams(List<RamlParam> fileParams) {
		this.fileParams = fileParams;
	}

}
