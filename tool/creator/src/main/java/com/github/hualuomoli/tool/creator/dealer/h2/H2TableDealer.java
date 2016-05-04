package com.github.hualuomoli.tool.creator.dealer.h2;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.DBColumn;
import com.github.hualuomoli.tool.creator.dealer.TableDealer;
import com.github.hualuomoli.tool.creator.dealer.TableDealerAbstract;

/**
 * H2数据库列的工具
 * @author hualuomoli
 *
 */
public class H2TableDealer extends TableDealerAbstract implements TableDealer {

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
			return "int";

		// long
		case "java.lang.Long":
		case "long":
			return "bigint";

		// double
		case "java.lang.Double":
		case "double":
			return "decimal";

		// Date
		case "java.util.Date":
			return "TIMESTAMP";

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
