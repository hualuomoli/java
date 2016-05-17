package com.github.hualuomoli.tool.creator.entity;

/**
 * 数据库列
 * @author hualuomoli
 *
 */
public class Column {

	private String name; // 名称
	private String type; // 类型
	private String comment; // 注释
	private boolean notNull; // 是否允许为空
	private String length; // 长度,如varchar(64) number(9,3)
	private String defaultValue; // 默认值

	public Column() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
