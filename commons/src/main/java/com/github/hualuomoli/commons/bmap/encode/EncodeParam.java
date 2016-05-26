package com.github.hualuomoli.commons.bmap.encode;

/**
 * 地理编码参数
 * @author hualuomoli
 *
 */
public class EncodeParam {

	private String address;
	private String city;

	public EncodeParam() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
