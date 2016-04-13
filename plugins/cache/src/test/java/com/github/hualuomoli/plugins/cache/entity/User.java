package com.github.hualuomoli.plugins.cache.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1547625777416274005L;

	private String username;
	private String nickaname;
	private String address;

	public User() {
	}

	public User(String username, String nickaname, String address) {
		this.username = username;
		this.nickaname = nickaname;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickaname() {
		return nickaname;
	}

	public void setNickaname(String nickaname) {
		this.nickaname = nickaname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
