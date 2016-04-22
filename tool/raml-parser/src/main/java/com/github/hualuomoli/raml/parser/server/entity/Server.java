package com.github.hualuomoli.raml.parser.server.entity;

import com.github.hualuomoli.raml.parser.entity.Template;

/**
 * 解析server
 * @author hualuomoli
 *
 */
public class Server extends Template {

	private static final long serialVersionUID = -6895780843611645426L;

	private String requestPath; // 请求路径
	private int requestType; // 请求类型
	private String requestMimeType; // 请求协议
	private String responseMimeType; // 响应协议

	
	// response
	private String responseDescription;

	private String methodName; // 方法名

	public Server() {
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public String getRequestMimeType() {
		return requestMimeType;
	}

	public void setRequestMimeType(String requestMimeType) {
		this.requestMimeType = requestMimeType;
	}

	public String getResponseMimeType() {
		return responseMimeType;
	}

	public void setResponseMimeType(String responseMimeType) {
		this.responseMimeType = responseMimeType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
