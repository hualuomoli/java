package com.github.hualuomoli.tool.creator.dealer.mysql;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.Column;
import com.github.hualuomoli.base.annotation.Table;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.creator._Attribute;
import com.github.hualuomoli.tool.creator.db.DBColumn;
import com.github.hualuomoli.tool.creator.db.DBTable;
import com.github.hualuomoli.tool.creator.dealer.AbstractCreatorDealer;
import com.github.hualuomoli.tool.creator.util.AttributeUtils;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MysqlCreatorDealer extends AbstractCreatorDealer {

	private static final String tplPath = "tpl/creator/mysql";
	private String main = "main"; // 是否输出到main,默认为main,可设置为test

	public MysqlCreatorDealer() {
	}

	public MysqlCreatorDealer(Boolean test) {
		if (test) {
			main = "test";
		}
	}

	private List<DBTable> dbTableList = Lists.newArrayList();

	private Class<?> entityCls;
	private String projectPackageName;
	private String outputPath;
	private List<_Attribute> attributes;

	@Override
	public void config(Class<?> entityCls, String projectPackageName, String outputPath) {
		this.entityCls = entityCls;
		this.projectPackageName = projectPackageName;
		this.outputPath = outputPath;
		attributes = AttributeUtils.getAttributes(entityCls, projectPackageName);
		// parse db
		this._parseDB();
	}

	@Override
	public void createEntity() {

		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", projectPackageName + ".base.entity"); // 包名
		map.put("javaName", "Base" + entityCls.getSimpleName()); // 类名
		map.put("extends", entityCls.getName()); // 继承类

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + projectPackageName.replaceAll("[.]", "/") + "/base/entity");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "entity.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + ".java"));
	}

	@Override
	public void createXml() {

		Map<String, Object> map = Maps.newHashMap();
		map.put("attributes", attributes); // 属性
		Table table = entityCls.getAnnotation(Table.class);
		map.put("dbName", table == null ? CreatorUtils.unCamel(entityCls.getSimpleName()) : table.name());

		// entity
		map.put("entityPackageName", projectPackageName + ".base.entity"); // 包名
		map.put("entityJavaName", "Base" + entityCls.getSimpleName()); // 类名

		// mapper
		map.put("mapperPackageName", projectPackageName + ".base.mapper"); // 包名
		map.put("mapperJavaName", "Base" + entityCls.getSimpleName() + "Mapper"); // 类名

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/resources/mappers/base");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "xml.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Mapper.xml"));

	}

	@Override
	public void createDao() {

		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", projectPackageName + ".base.mapper"); // 包名
		map.put("javaName", "Base" + entityCls.getSimpleName() + "Mapper"); // 类名

		// entity
		map.put("entityPackageName", projectPackageName + ".base.entity"); // 包名
		map.put("entityJavaName", "Base" + entityCls.getSimpleName()); // 类名

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + projectPackageName.replaceAll("[.]", "/") + "/base/mapper");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "dao.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Mapper.java"));

	}

	@Override
	public void createService() {

		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", projectPackageName + ".base.service"); // 包名
		map.put("javaName", "Base" + entityCls.getSimpleName() + "Service"); // 类名

		// entity
		map.put("entityPackageName", projectPackageName + ".base.entity"); // 包名
		map.put("entityJavaName", "Base" + entityCls.getSimpleName()); // 类名

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + projectPackageName.replaceAll("[.]", "/") + "/base/service");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "service.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "Service.java"));

	}

	@Override
	public void createServiceImpl() {

		Map<String, Object> map = Maps.newHashMap();
		map.put("packageName", projectPackageName + ".base.service"); // 包名
		map.put("javaName", "Base" + entityCls.getSimpleName() + "ServiceImpl"); // 类名
		map.put("implements", projectPackageName + ".base.service." + "Base" + entityCls.getSimpleName() + "Service");

		// entity
		map.put("entityPackageName", projectPackageName + ".base.entity"); // 包名
		map.put("entityJavaName", "Base" + entityCls.getSimpleName()); // 类名

		// mapper
		map.put("mapperPackageName", projectPackageName + ".base.mapper"); // 包名
		map.put("mapperJavaName", "Base" + entityCls.getSimpleName() + "Mapper"); // 类名

		// 创建目录
		File dir = new File(outputPath, "src/" + main + "/java/" + projectPackageName.replaceAll("[.]", "/") + "/base/service");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		TemplateUtils.processByResource(tplPath, "serviceImpl.tpl", map, new File(dir.getAbsolutePath(), "Base" + entityCls.getSimpleName() + "ServiceImpl.java"));

	}

	@Override
	protected void createDB() {

		// 创建目录
		File dir = new File(outputPath, "/doc");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 输出
		Map<String, Object> map = Maps.newHashMap();
		map.put("tableList", dbTableList);
		TemplateUtils.processByResource(tplPath, "database.tpl", map, new File(dir.getAbsolutePath(), "database_init.sql"));

	}

	private void _parseDB() {
		DBTable dbTable = new DBTable();
		// 数据库列
		List<DBColumn> columnList = Lists.newArrayList();
		for (_Attribute attribute : attributes) {
			columnList.add(DBParser.parse(attribute, projectPackageName));
		}
		dbTable.setColumnList(columnList);
		// 表信息
		Table table = entityCls.getAnnotation(Table.class);
		if (table == null) {
			dbTable.setName(CreatorUtils.unCamel(entityCls.getSimpleName()));
			dbTable.setComment("");
		} else {
			dbTable.setName(table.name());
			dbTable.setComment(table.comment());
		}
		// add
		dbTableList.add(dbTable);
	}

	// 数据库解析器
	static final class DBParser {

		// 解析属性为数据库列
		static final DBColumn parse(_Attribute attribute, String projectPackageName) {
			String name = attribute.getField().getType().getName();
			switch (name) {
			case "java.lang.Integer": // Integer
				return _parseInteger(attribute);
			case "java.lang.Long": // Long
				return _parseLong(attribute);
			case "java.lang.Double": // Double
				return _parseDouble(attribute);
			case "java.lang.String": // String
				return _parseString(attribute);
			case "java.util.Date": // Date
				return _parseDate(attribute);
			default:
				break;
			}
			if (name.startsWith(projectPackageName)) {
				return _parseEntity(attribute);
			}
			throw new RuntimeException("can not support " + attribute.getField().getType().getName());
		}

		private static final DBColumn _parseInteger(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("integer");
			dbColumn.setLength("11");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue(column.defaultValue());
				}
				// length
				if (column.precision() > 0) {
					dbColumn.setLength(String.valueOf(column.precision()));
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseLong(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("bigint");
			dbColumn.setLength("20");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue(column.defaultValue());
				}
				// length
				if (column.precision() > 0) {
					dbColumn.setLength(String.valueOf(column.precision()));
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseDouble(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("double");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue(column.defaultValue());
				}
				// length
				if (column.precision() > 0 && column.scale() > 0) {
					dbColumn.setLength(column.precision() + "," + column.scale());
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseString(_Attribute attribute) {
			Column column = attribute.getField().getAnnotation(Column.class);
			if (column == null) {
				return _parseVarchar(attribute);
			}

			// 判断type
			switch (column.type()) {
			case CHAR: // char
				return _parseChar(attribute);
			case CLOB: // long text
				return _parseLongText(attribute);
			default:
				break;
			}

			// 判断长度
			int length = column.length();
			if (length == 1) { // char
				return _parseChar(attribute);
			} else if (length > 1 && length <= 200) { // varchar
				return _parseVarchar(attribute);
			} else if (length > 200 && length <= 4000) { // text
				return _parseText(attribute);
			} else if (length > 4000) { // long text
				return _parseLongText(attribute);
			}

			return _parseVarchar(attribute);
		}

		private static final DBColumn _parseVarchar(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("varchar");
			dbColumn.setLength("32");

			Column column = attribute.getField().getAnnotation(Column.class);

			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue("'" + column.defaultValue() + "'");
				}
				// length
				if (column.length() > 0) {
					dbColumn.setLength(String.valueOf(column.length()));
				}
			}

			return dbColumn;
		}

		private static final DBColumn _parseChar(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("varchar");
			dbColumn.setLength("1");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue("'" + column.defaultValue() + "'");
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseText(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("text");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue("'" + column.defaultValue() + "'");
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseLongText(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("longtext");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue("'" + column.defaultValue() + "'");
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseDate(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName());
			dbColumn.setType("timestamp");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// type
				switch (column.type()) {
				case TIMESTAMP:
					dbColumn.setType("timestamp");
					break;
				case DATE:
					dbColumn.setType("date");
					break;
				case TIME:
					dbColumn.setType("time");
				default:
					break;
				}
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// default value
				if (StringUtils.isNotBlank(column.defaultValue())) {
					dbColumn.setDefaultValue(column.defaultValue());
				}
			}
			return dbColumn;
		}

		private static final DBColumn _parseEntity(_Attribute attribute) {
			DBColumn dbColumn = new DBColumn();
			dbColumn.setName(attribute.getDbName() + "_id");
			dbColumn.setType("varchar");
			dbColumn.setLength("32");

			Column column = attribute.getField().getAnnotation(Column.class);
			// 根据注解设置
			if (column != null) {
				// comment
				if (StringUtils.isNotBlank(column.comment())) {
					dbColumn.setComment(column.comment());
				}
				// nullable
				if (!column.nullable()) {
					dbColumn.setNullable("N");
				}
				// length
				if (column.length() > 0) {
					dbColumn.setLength(String.valueOf(column.length()));
				}
			}
			return dbColumn;
		}

	}

}
