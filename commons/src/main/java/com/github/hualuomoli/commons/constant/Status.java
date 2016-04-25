package com.github.hualuomoli.commons.constant;

/**
 * 数据状态
 * @author hualuomoli
 *
 */
public enum Status {

	NOMAL(1), // 正常状态
	DELETED(2), // 删除状态
	DISABLE(3), // 不可用(禁用)
	ENABLE(4), // 可用(启用)
	;

	private int value;

	private Status(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
