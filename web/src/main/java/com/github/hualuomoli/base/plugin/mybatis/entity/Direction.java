package com.github.hualuomoli.base.plugin.mybatis.entity;

/**
 * 方向
 * @author hualuomoli
 *
 */
public enum Direction {

	ASC("asc"), //
	DESC("desc"), //
	;

	private String value;

	private Direction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
