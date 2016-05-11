package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

/**
 * 实体类
 * @author hualuomoli
 *
 */
public class Entity {

	private Class<?> cls; // 实体类的class
	private String simpleName; // 实体类的简称
	private String fullName; // 实体类的全名称
	private String dbName; // 实体类对应数据库表名
	private List<Attribute> attributes; // 属性集合

	public Entity() {
	}

	public Entity(Class<?> cls) {
		this.cls = cls;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
