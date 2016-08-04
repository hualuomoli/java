package com.github.hualuomoli.tool.creator.entity;

import java.util.List;

/**
 * 数据库表
 * @author hualuomoli
 *
 */
public class DBTable {

	private String name;
	private String comment;
	private DBColumn unique; // 唯一列
	private List<DBColumn> unionUnique; // 联合唯一列
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

	public DBColumn getUnique() {
		return unique;
	}

	public void setUnique(DBColumn unique) {
		this.unique = unique;
	}

	public List<DBColumn> getUnionUnique() {
		return unionUnique;
	}

	public void setUnionUnique(List<DBColumn> unionUnique) {
		this.unionUnique = unionUnique;
	}

}
