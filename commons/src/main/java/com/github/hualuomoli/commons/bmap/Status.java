package com.github.hualuomoli.commons.bmap;

/**
 * 百度地图返回状态码
 * @author hualuomoli
 *
 */
public enum Status {

	SUCCESS(0); // 成功

	private int code;

	private Status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
