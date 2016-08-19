package com.github.hualuomoli.extend.constant;

/**
 * 数据类型
 * @author hualuomoli
 *
 */
public enum DataType {

	MENU_TYPE_MENU(1, "菜单"), // 菜单
	MENU_TYPE_PERMISSION(2, "权限"), // 权限
	USER_SEX_MALE(1, "M"), // 男
	USER_SEX_FEMALE(2, "F"),// 女
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
