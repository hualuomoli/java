package com.github.hualuomoli.tool.creator.db;

import java.util.List;

/**
 * 数据库表
 * @author hualuomoli
 *
 */
public class DBTable {

	private String name;
	private String comment;
	private List<DBColumn> columnList;

	public DBTable() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<DBColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DBColumn> columnList) {
		this.columnList = columnList;
	}

}
