package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

public class CreatorTable {

	private String dbName;
	private String comments;
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

}
