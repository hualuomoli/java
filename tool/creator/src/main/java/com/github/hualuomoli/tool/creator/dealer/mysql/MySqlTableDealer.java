package com.github.hualuomoli.tool.creator.dealer.mysql;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.DBColumn;
import com.github.hualuomoli.tool.creator.dealer.TableDealer;
import com.github.hualuomoli.tool.creator.dealer.TableDealerAbstract;

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

}
