package com.github.hualuomoli.base.annotation.entity;

/**
 * 列类型
 * @author hualuomoli
 *
 */
public enum AEntityColumnType {

	AUTO(), // 自动转换
	CHAR(), // 单个字符
	STRING(), // 字符串
	TIMESTAMP(), // 时间戳
	DATE_TIME(), // 日期,时间
	DATE(), // 日期
	TIME(), // 时间
	TEXT(), // 大字符
	LONG_TEXT(), // 大字符
	CLOB(), // 大字符
	;

	private AEntityColumnType() {
	}

}
