package com.github.hualuomoli.tool.creator.entity;

/**
 * 实体类属性
 * @author hualuomoli
 *
 */
public class Attribute {

	private String name; // 实体类属性名
	private String blanks; // 实体类属性空白
	private String dbName; // 数据库列名
	private String dbBlanks; // 数据库列空白
	private boolean string; // 是否是字符串

	public Attribute() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlanks() {
		return blanks;
	}

	public void setBlanks(String blanks) {
		this.blanks = blanks;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbBlanks() {
		return dbBlanks;
	}

	public void setDbBlanks(String dbBlanks) {
		this.dbBlanks = dbBlanks;
	}

	public boolean isString() {
		return string;
	}

	public void setString(boolean string) {
		this.string = string;
	}

}
