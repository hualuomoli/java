package com.github.hualuomoli.base.constant;

/**
 * 数据状态
 * @author hualuomoli
 *
 */
public enum Status {

	NOMAL(1), // 正常
	DELETED(2), // 已删除
	DISABLE(3), // 禁用
	;

	private Integer value;

	private Status(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
