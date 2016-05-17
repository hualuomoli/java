package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

/**
 * 数据库表
 * @author hualuomoli
 *
 */
public class Table {

	private String name;
	private String comment;
	private List<Column> columnList;

	public Table() {
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

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

}
