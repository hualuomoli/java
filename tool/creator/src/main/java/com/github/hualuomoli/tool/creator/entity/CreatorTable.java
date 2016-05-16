package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

public class CreatorTable {

	private String dbName;
	private String comments;
	private List<CreatorColumn> columns;

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

}
