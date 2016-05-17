package com.github.hualuomoli.tool.creator.dealer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.Comment;
import com.github.hualuomoli.base.annotation.DBColumn;
import com.github.hualuomoli.tool.creator.entity.Column;
import com.github.hualuomoli.tool.creator.entity.Table;
import com.github.hualuomoli.tool.creator.util.AttributeUtils;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Mysql数据库列的工具
 * @author hualuomoli
 *
 */
public class MySqlTableDealer extends CreatorUtils implements TableDealer {

	@Override
	public Table getTable(Class<?> entityCls, Set<String> ignores, String projectPackageName) {
		Table table = new Table();

		Comment comment = entityCls.getAnnotation(Comment.class);

		table.setName(unCamel(entityCls.getSimpleName()));
		table.setComment(comment == null ? "" : comment.value());
		table.setColumnList(this.getColumns(entityCls, ignores, projectPackageName));

		return table;
	}

	/**
	 * 获取类的Column
	 * @param entityCls class
	 * @param ignores 忽略属性
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 是否有效
	 */
	@Override
	public List<Column> getColumns(Class<?> entityCls, Set<String> ignores, String projectPackageName) {

		List<Column> columns = Lists.newArrayList();

		if (ignores == null) {
			ignores = Sets.newHashSet();
		}
		if (entityCls == null) {
			return columns;
		}

		Field[] fields = entityCls.getDeclaredFields();
		for (Field field : fields) {
			Column column = new Column();
			// ignore
			if (ignores.contains(field.getName())) {
				continue;
			}
			// valid
			if (!AttributeUtils.valid(field, entityCls)) {
				continue;
			}
			// List<Bean> beans
			// 一对多
			if (field.getType() == java.util.List.class || field.getType() == java.util.ArrayList.class) {
				continue;
			}

			// 注解
			Comment comment = field.getAnnotation(Comment.class);
			DBColumn dBColumn = field.getAnnotation(DBColumn.class);

			column.setComment(comment == null ? "" : comment.value()); // 注释
			column.setNotNull(dBColumn == null ? false : dBColumn.value()); // 是否为空

			// 一对一
			if (StringUtils.startsWith(field.getType().getName(), projectPackageName)) {
				String attributeName = field.getName();

				column.setName(unCamel(attributeName) + "_id");
				column.setType("varchar");
				column.setLength("32"); // id32位

			} else {
				String attributeName = field.getName();

				column.setName(unCamel(attributeName));

				switch (field.getType().getSimpleName()) {
				case "String":
					column.setType("varchar");
					column.setLength(dBColumn == null ? "32" : String.valueOf(dBColumn.length()));

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue("'" + dBColumn.defaultValue() + "'");
					}

					break;
				case "Integer":
				case "int":
					column.setType("integer");
					column.setLength(dBColumn == null ? "11" : String.valueOf(dBColumn.precision()));

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue(dBColumn.defaultValue());
					}

					break;
				case "Long":
				case "long":
					column.setType("bigint");
					column.setLength(dBColumn == null ? "23" : String.valueOf(dBColumn.precision()));

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue(dBColumn.defaultValue());
					}

					break;
				case "Double":
				case "double":
					column.setType("double");
					column.setLength(dBColumn == null ? "11,3" : dBColumn.precision() + "," + dBColumn.scale());

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue(dBColumn.defaultValue());
					}

					break;
				case "Boolean":
				case "boolean":
					column.setType("char");
					column.setLength("1");

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue("'" + dBColumn.defaultValue() + "'");
					}

					break;
				case "Date":
					column.setType("datetime");

					if (dBColumn != null && dBColumn.defaultValue().length() > 0) {
						column.setDefaultValue(dBColumn.defaultValue());
					}

					break;
				default:
					break;

				}
				//
			}

			// 判断是否设置了数据类型
			if (dBColumn != null && dBColumn.type().length() > 0) {
				column.setType(dBColumn.type());
			}

			columns.add(column);

		}
		columns.addAll(getColumns(entityCls.getSuperclass(), ignores, projectPackageName));

		return columns;

	}

}
