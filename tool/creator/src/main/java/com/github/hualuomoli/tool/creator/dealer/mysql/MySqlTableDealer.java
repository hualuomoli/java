package com.github.hualuomoli.tool.creator.dealer.mysql;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.DBColumn;
import com.github.hualuomoli.tool.creator.dealer.TableDealer;
import com.github.hualuomoli.tool.creator.dealer.TableDealerAbstract;
import com.github.hualuomoli.tool.creator.entity.Column;
import com.google.common.collect.Lists;

/**
 * Mysql数据库列的工具
 * @author hualuomoli
 *
 */
public class MySqlTableDealer extends TableDealerAbstract implements TableDealer {

	@Override
	public String getDefaultColumnType(Class<?> columnClass, DBColumn dbColumn) {

		if (dbColumn != null && StringUtils.isNotBlank(dbColumn.type())) {
			return dbColumn.type();
		}

		switch (columnClass.getName()) {

		// string
		case "java.lang.String":
			return "varchar";

		// int
		case "java.lang.Integer":
		case "int":
			return "integer";

		// long
		case "java.lang.Long":
		case "long":
			return "bigint";

		// double
		case "java.lang.Double":
		case "double":
			return "double";

		// Date
		case "java.util.Date":
			return "datetime";

		default:
			throw new RuntimeException("not support type " + columnClass.getName());
		}

	}

	@Override
	public String getDefaultColumnLength(Class<?> columnClass) {

		switch (columnClass.getName()) {

		// string
		case "java.lang.String":
			return "32";

		// int
		case "java.lang.Integer":
		case "int":
			return "11";

		// long
		case "java.lang.Long":
		case "long":
			return "17";

		// double
		case "java.lang.Double":
		case "double":
			return "11,3";

		// Date
		case "java.util.Date":
			return "";

		default:
			throw new RuntimeException("not support type " + columnClass.getName());
		}

	}

	@Override
	protected int getUserLength() {
		return 32;
	}

	// add version,createBy,createDate,updateBy,updateDate,status,remark
	protected List<Column> getCommonColumns(Set<String> ignores) {
		List<Column> columns = Lists.newArrayList();

		// createBy
		if (!ignores.contains("createBy")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(this.getUserLength()), unCamel("createBy"), "创建人", true, "'system'"));
		}
		// createDate
		if (!ignores.contains("createDate")) {
			columns.add(this.getColumn(Date.class, new MyDBColumn(), unCamel("create_date"), "创建时间", true, "CURRENT_TIMESTAMP"));
		}
		// updateBy
		if (!ignores.contains("updateBy")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(this.getUserLength()), unCamel("updateBy"), "修改人", true, "'system'"));
		}
		// updateDate
		if (!ignores.contains("updateDate")) {
			columns.add(this.getColumn(Date.class, new MyDBColumn(), unCamel("updateDate"), "修改时间", true, "CURRENT_TIMESTAMP"));
		}
		// status
		if (!ignores.contains("status")) {
			columns.add(this.getColumn(Integer.class, new MyDBColumn(1), unCamel("status"), "数据状态 0无效,1有效,2删除", true, "1"));
		}
		// remark
		if (!ignores.contains("remark")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(2000), unCamel("remark"), "备注", false, ""));
		}
		return columns;
	}

}
