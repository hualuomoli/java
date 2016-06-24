package com.github.hualuomoli.tool.raml.entity;

import java.io.Serializable;

public class RamlMethodMimeType implements Serializable {

	private static final long serialVersionUID = -7728897819222409148L;

	private String uri;
	private String method;
	private String consumes;
	private String produces;

	public RamlMethodMimeType() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getConsumes() {
		return consumes;
	}

	public void setConsumes(String consumes) {
		this.consumes = consumes;
	}

	public String getProduces() {
		return produces;
	}

	public void setProduces(String produces) {
		this.produces = produces;
	}

}
