package com.github.hualuomoli.tool.creator.entity;

import java.lang.reflect.Field;

/**
 * 实体类属性
 * @author hualuomoli
 *
 */
public class CreatorColumn {

	private Field field; // 属性
	// java
	private String javaTypeName; // 类型名称
	private String javaName; // 名称
	private int javaNameLength; // 长度
	private String javaBlanks; // 需要补充空白长度

	// db
	private String dbName; // 名称
	private int dbNameLength; // 长度
	private String dbBlanks; // 需要补充空白长度

	// 其他信息
	private String comment;// 注释
	private boolean custom; // 是否是自定义类型
	private boolean string; // 是否是字符串
	private boolean entity; // 是否是其他entity
	private String relation; // 关联属性

	private String typeHandler; // 类型解析处理handler

	public CreatorColumn() {
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public int getJavaNameLength() {
		return javaNameLength;
	}

	public void setJavaNameLength(int javaNameLength) {
		this.javaNameLength = javaNameLength;
	}

	public String getJavaBlanks() {
		return javaBlanks;
	}

	public void setJavaBlanks(String javaBlanks) {
		this.javaBlanks = javaBlanks;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getJavaTypeName() {
		return javaTypeName;
	}

	public void setJavaTypeName(String javaTypeName) {
		this.javaTypeName = javaTypeName;
	}

	public String getTypeHandler() {
		return typeHandler;
	}

	public void setTypeHandler(String typeHandler) {
		this.typeHandler = typeHandler;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

}
