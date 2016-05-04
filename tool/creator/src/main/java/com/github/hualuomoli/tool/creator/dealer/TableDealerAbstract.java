package com.github.hualuomoli.tool.creator.dealer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
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
 * 数据库列的工具
 * @author hualuomoli
 *
 */
public abstract class TableDealerAbstract extends CreatorUtils implements TableDealer {

	/**
	 * 主键ID的长度
	 * @return 默认为32
	 */
	protected int getIdLength() {
		return 32;
	}

	/**
	 * 获取用户的长度,如用户的昵称
	 * @return 默认为12位
	 */
	protected int getUserLength() {
		return 12;
	}

	/**
	 * 获取JAVA类型对应的数据库列类型,如varchar
	 * @param columnClass 属性的class类型
	 * @param dbColumn 列注解
	 * @return 默认的数据库列类型
	 */
	public abstract String getDefaultColumnType(Class<?> columnClass, DBColumn dbColumn);

	/**
	 * 获取JAVA类型对应的数据库列类型,如varchar
	 * @param columnClass 属性的class类型
	 * @param dbColumn 列注解
	 * @return 默认的数据库列类型
	 */
	public String getColumnType(Class<?> columnClass) {
		return this.getDefaultColumnType(columnClass, null);
	}

	/**
	 * 获取JAVA类型对应的数据库列长度,多个长度用逗号(,)分割
	 * string 类型数据只有一个长度
	 * double 类型数据有两个长度(精度和标度)
	 * @param columnClass 属性的class类型
	 * @return 默认的数据库列长度
	 */
	public abstract String getDefaultColumnLength(Class<?> columnClass);

	/**
	 * 获取JAVA类型对应的数据库列长度,多个长度用逗号(,)分割
	 * @param columnClass 属性的class类型
	 * @param dbColumn 列注解
	 * @return 默认的数据库列长度
	 */
	protected String getColumnLength(Class<?> columnClass, DBColumn dbColumn) {

		if (dbColumn == null) {
			return this.getDefaultColumnLength(columnClass);
		}

		int length = 0;
		int precision = 0;
		int scale = 0;

		switch (columnClass.getSimpleName()) {
		case "String":
			length = dbColumn.length();
			break;
		case "Integer":
		case "int":
			length = dbColumn.length();
			precision = dbColumn.precision();
			break;
		case "Long":
		case "long":
			length = dbColumn.length();
			precision = dbColumn.precision();
			break;
		case "Double":
		case "double":
			precision = dbColumn.precision();
			scale = dbColumn.scale();
			break;
		}

		// there is not set length, use default
		if (length + precision + scale == 0) {
			return this.getDefaultColumnLength(columnClass);
		}

		// set length
		if (length > 0) {
			return String.valueOf(length);
		}

		if (precision > 0 && scale == 0) {
			return String.valueOf(precision);
		}
		if (precision > 0 && scale > 0) {
			return precision + "," + scale;
		}
		return "";
	}

	// 获取注释
	private String getComment(Comment comment) {
		return comment == null ? "" : comment.value();
	}

	// 获取注释
	private String getComment(Class<?> cls) {
		return getComment(cls.getAnnotation(Comment.class));
	}

	// 获取注释
	private String getComment(Field field) {
		return getComment(field.getAnnotation(Comment.class));
	}

	// 获取列
	private Column getColumn(Class<?> cls, DBColumn dbColumn, String name, String comment, boolean notNull, String defaultValue) {
		Column column = new Column();

		column.setName(name);
		column.setType(this.getDefaultColumnType(cls, dbColumn));
		column.setComment(comment);
		column.setNotNull(notNull);
		column.setLength(this.getColumnLength(cls, dbColumn));
		column.setDefaultValue(defaultValue);

		return column;
	}

	@Override
	public Table getTable(Class<?> entityCls, Set<String> ignores, String projectPackageName) {
		Table table = new Table();

		table.setName(unCamel(entityCls.getSimpleName())); // 表名
		table.setComment(this.getComment(entityCls)); // 注释

		// remove baseEntity attribute
		Set<String> sets = Sets.newHashSet(ignores);
		sets.add("pagination");
		sets.add("id");
		sets.add("version");
		sets.add("createBy");
		sets.add("createDate");
		sets.add("updateBy");
		sets.add("updateDate");
		sets.add("status");
		sets.add("remark");

		// get columns
		List<Column> columnList = this.getColumns(entityCls, sets, projectPackageName);
		// add id
		columnList.add(0, this.getColumn(String.class, new MyDBColumn(this.getIdLength()), "id", "主键", true, ""));
		// add version
		if (!ignores.contains("version")) {
			columnList.add(1, this.getColumn(Integer.class, new MyDBColumn(11), "version", "数据版本", true, "1"));
		}
		// add version,createBy,createDate,updateBy,updateDate,status,remark
		columnList.addAll(this.getCommonColumns(ignores));

		// set
		table.setColumnList(columnList);

		return table;
	}

	// add version,createBy,createDate,updateBy,updateDate,status,remark
	private List<Column> getCommonColumns(Set<String> ignores) {
		List<Column> columns = Lists.newArrayList();

		// createBy
		if (!ignores.contains("createBy")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(this.getUserLength()), "createBy", "创建人", true, ""));
		}
		// createDate
		if (!ignores.contains("createDate")) {
			columns.add(this.getColumn(Date.class, new MyDBColumn(), "createDate", "创建时间", true, ""));
		}
		// updateBy
		if (!ignores.contains("updateBy")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(this.getUserLength()), "updateBy", "修改人", true, ""));
		}
		// updateDate
		if (!ignores.contains("updateDate")) {
			columns.add(this.getColumn(Date.class, new MyDBColumn(), "updateDate", "修改时间", true, ""));
		}
		// status
		if (!ignores.contains("status")) {
			columns.add(this.getColumn(Integer.class, new MyDBColumn(1), "status", "数据状态 0无效,1有效,2删除", true, "1"));
		}
		// remark
		if (!ignores.contains("remark")) {
			columns.add(this.getColumn(String.class, new MyDBColumn(2000), "remark", "备注", false, ""));
		}
		return columns;
	}

	// 自定义注解
	private class MyDBColumn implements DBColumn {

		private int length;
		private int precision;
		private int scale;

		private MyDBColumn() {
		}

		private MyDBColumn(int length) {
			this.length = length;
		}

		// public MyDBColumn(int precision, int scale) {
		// this.precision = precision;
		// this.scale = scale;
		// }

		@Override
		public Class<? extends Annotation> annotationType() {
			return null;
		}

		@Override
		public boolean value() {
			return false;
		}

		@Override
		public String type() {
			return null;
		}

		@Override
		public int length() {
			return length;
		}

		@Override
		public int precision() {
			return precision;
		}

		@Override
		public int scale() {
			return scale;
		}

		@Override
		public String defaultValue() {
			return null;
		}

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

			Class<?> columnClass = field.getType();

			// List<Bean> beans
			// 一对多
			if (columnClass == java.util.List.class || columnClass == java.util.ArrayList.class) {
				continue;
			}

			// 注解
			DBColumn dBColumn = field.getAnnotation(DBColumn.class);

			String comment = this.getComment(field);// 注释
			boolean notNull = dBColumn == null ? false : dBColumn.value();// 是否为空
			String defaultValue = dBColumn == null ? "" : dBColumn.defaultValue(); // 默认值

			if (StringUtils.startsWith(field.getType().getName(), projectPackageName)) {
				// 一对一 其他实体类
				String attributeName = field.getName();
				String name = unCamel(attributeName) + "_id";
				column = this.getColumn(String.class, new MyDBColumn(this.getIdLength()), name, comment, notNull, "");
			} else {
				// 简单类型
				String attributeName = field.getName();
				String name = unCamel(attributeName);
				column = this.getColumn(columnClass, dBColumn, name, comment, notNull, defaultValue);
			}

			columns.add(column);

		}
		columns.addAll(getColumns(entityCls.getSuperclass(), ignores, projectPackageName));

		return columns;

	}

}
