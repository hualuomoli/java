package com.github.hualuomoli.base.constant;

/**
 * 数据状态
 * @author hualuomoli
 *
 */
public enum Status {

	NOMAL(1, "正常"), // 正常
	DELETED(2, "已删除"), // 已删除
	DISABLE(3, "禁用"), // 禁用
	ABANDON(4, "废弃"), // 废弃
	PENDING(5, "待审批"), // 待审批
	;

	private Integer value;
	private String name;

	private Status(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
