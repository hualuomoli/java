package com.github.hualuomoli.base.annotation;

public enum EntityType {

	AUTO(), // 自动转换
	CHAR(), // 单个字符
	STRING(), // 字符串
	TIMESTAMP(), // 时间戳
	DATE(), // 日期
	TIME(), // 日期
	CLOB(), // 大字符
	;

	private EntityType() {
	}

}
