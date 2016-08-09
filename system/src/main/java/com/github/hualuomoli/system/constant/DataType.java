package com.github.hualuomoli.system.constant;

/**
 * 数据类型
 * @author hualuomoli
 *
 */
public enum DataType {

	MENU_ROOT_VALUE(0, "0"), // 顶级的父节点编码
	MENU_TYPE_MENU(1, "菜单"), // 菜单
	MENU_TYPE_PERMISSION(1, "权限"),// 权限
	;

	private Integer value;
	private String name;

	private DataType(Integer value, String name) {
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
