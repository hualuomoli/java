package com.github.hualuomoli.plugins.mq.activemq.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

// serial entity
public class Order implements Serializable {

	private static final long serialVersionUID = 8906877543641181869L;

	private String id;
	private String serialNumber;
	private String name;

	public Order() {
	}

	public Order(String id, String serialNumber, String name) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
