package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

public class CreatorTable {

	public static final Integer ENTITY_TYPE_BASE = 1;
	public static final Integer ENTITY_TYPE_COMMON = 2;

	private String dbName;
	private String comments;
	private Integer entityType; // 实体类类型 1=base,2=common
	private boolean tree; // 是否是树
	private List<CreatorColumn> columns;
	private List<CreatorColumnQuery> queryColumns;

	public CreatorTable() {
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<CreatorColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<CreatorColumn> columns) {
		this.columns = columns;
	}

	public List<CreatorColumnQuery> getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(List<CreatorColumnQuery> queryColumns) {
		this.queryColumns = queryColumns;
	}

	public Integer getEntityType() {
		return entityType;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public boolean isTree() {
		return tree;
	}

	public void setTree(boolean tree) {
		this.tree = tree;
	}

}
