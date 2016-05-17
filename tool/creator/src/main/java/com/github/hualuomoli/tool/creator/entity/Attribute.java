package com.github.hualuomoli.tool.creator.entity;

/**
 * 实体类属性
 * @author hualuomoli
 *
 */
public class Attribute {

	// 实体类
	private String name; // 实体类属性名
	private int nameLength; // 实体类属性名 长度
	private String blanks; // 实体类属性空白

	// 数据库列
	private String dbName; // 数据库列名
	private int dbNameLength; // 数据库列名 长度
	private String dbBlanks; // 数据库列空白

	// 其他信息
	private boolean string; // 是否是字符串
	private boolean entity; // 是否是其他entity

	public Attribute() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNameLength() {
		return nameLength;
	}

	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
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

	public int getDbNameLength() {
		return dbNameLength;
	}

	public void setDbNameLength(int dbNameLength) {
		this.dbNameLength = dbNameLength;
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

	public boolean isEntity() {
		return entity;
	}

	public void setEntity(boolean entity) {
		this.entity = entity;
	}

}
