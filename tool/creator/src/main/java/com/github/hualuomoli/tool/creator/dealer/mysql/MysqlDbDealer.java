package com.github.hualuomoli.tool.creator.dealer.mysql;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;
import com.github.hualuomoli.commons.util.TemplateUtils;
import com.github.hualuomoli.tool.creator.dealer.DbDealer;
import com.github.hualuomoli.tool.creator.entity.CreatorColumn;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.github.hualuomoli.tool.creator.entity.DBColumn;
import com.github.hualuomoli.tool.creator.entity.DBTable;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MysqlDbDealer implements DbDealer {

	private static final String tplPath = "tpl/creator/mysql";

	private static final int DEFAULT_LENGTH_INTEGER = 11;
	private static final int DEFAULT_LENGTH_LONG = 18;
	private static final int DEFAULT_LENGTH_DOUBLE_PRECISION = 8;
	private static final int DEFAULT_LENGTH_DOUBLE_SCALE = 3;
	private static final int DEFAULT_LENGTH_STRING = 32;

	private DbConfig dbConfig;

	@Override
	public void setDbConfig(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	@Override
	public void execute(List<Class<?>> clsList, List<CreatorTable> tableList) {
		List<DBTable> dBTableList = Lists.newArrayList();
		for (int i = 0; i < clsList.size(); i++) {
			dBTableList.add(this.getDbTable(clsList.get(i), tableList.get(i)));
		}

		this.createTable(dBTableList);
	}

	// 创建表结构
	private void createTable(List<DBTable> dBTableList) {

		Map<String, Object> map = Maps.newHashMap();
		map.put("tableList", dBTableList);

		// 创建目录
		File dir = new File(this.dbConfig.output, this.dbConfig.dbPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 输出
		TemplateUtils.processByResource(tplPath, "db.tpl", map, new File(dir.getAbsolutePath(), "database.sql"));

	}

	// 获取表
	private DBTable getDbTable(Class<?> cls, CreatorTable creatorTable) {

		DBTable dBTable = new DBTable();
		dBTable.setName(creatorTable.getDbName());
		dBTable.setComment(creatorTable.getComments());

		List<DBColumn> columnList = Lists.newArrayList();

		List<CreatorColumn> columns = creatorTable.getColumns();
		for (CreatorColumn creatorColumn : columns) {
			columnList.add(this.getDBColumn(creatorColumn));
		}

		dBTable.setColumnList(columnList);

		return dBTable;
	}

	// 获取列
	private DBColumn getDBColumn(CreatorColumn creatorColumn) {

		DBColumn dBColumn = null;

		Field field = creatorColumn.getField();
		EntityColumn entityColumn = field.getAnnotation(EntityColumn.class);
		if (entityColumn == null) {
			dBColumn = this.getDefaultDBColumn(field);
		} else {
			dBColumn = this.getDBColumn(field, entityColumn);
		}

		dBColumn.setName(creatorColumn.getDbName());

		return dBColumn;
	}

	// 获取列
	private DBColumn getDBColumn(Field field, EntityColumn entityColumn) {

		DBColumn dBColumn = new DBColumn();
		dBColumn.setComment(entityColumn.comment());
		dBColumn.setNullable(entityColumn.nullable() ? "Y" : "N");

		String fieldClassName = field.getType().getName();
		EntityColumnType type = entityColumn.type();

		int length = entityColumn.length();
		int precision = entityColumn.precision();
		int scale = entityColumn.scale();
		String defaultValue = entityColumn.defaultValue();

		// 列类型
		switch (fieldClassName) {
		case "java.lang.Integer":
		case "int":
			dBColumn.setType("integer");
			dBColumn.setLength("(" + (length == 0 ? precision == 0 ? DEFAULT_LENGTH_INTEGER : precision : length) + ")");
			if (StringUtils.isNotBlank(defaultValue)) {
				dBColumn.setDefaultValue(defaultValue);
			}
			break;
		case "java.lang.Long":
		case "long":
			dBColumn.setType("integer");
			dBColumn.setLength("(" + (length == 0 ? precision == 0 ? DEFAULT_LENGTH_LONG : precision : length) + ")");
			if (StringUtils.isNotBlank(defaultValue)) {
				dBColumn.setDefaultValue(defaultValue);
			}
			break;
		case "java.lang.Double":
		case "double":
			dBColumn.setType("double");
			dBColumn.setLength(
					"(" + (precision == 0 ? DEFAULT_LENGTH_DOUBLE_PRECISION : precision) + "," + (scale == 0 ? DEFAULT_LENGTH_DOUBLE_SCALE : scale) + ")");
			if (StringUtils.isNotBlank(defaultValue)) {
				dBColumn.setDefaultValue(defaultValue);
			}
			break;
		case "java.util.Date":
			switch (type) {
			case TIMESTAMP:
				dBColumn.setType("timestamp");
				break;
			case DATE_TIME:
				dBColumn.setType("datetime");
				break;
			case DATE:
				dBColumn.setType("date");
				break;
			case TIME:
				dBColumn.setType("time");
				break;
			default:
				dBColumn.setType("timestamp");
			}
			if (StringUtils.isNotBlank(defaultValue)) {
				dBColumn.setDefaultValue(defaultValue);
			}
			break;
		case "java.lang.String":
			switch (type) {
			case CHAR:
				dBColumn.setType("char");
				dBColumn.setLength("(1)");
				break;
			case CLOB:
				dBColumn.setType("longtext");
				dBColumn.setLength("");
				break;
			case TEXT:
				dBColumn.setType("text");
				dBColumn.setLength("");
				break;
			case LONG_TEXT:
				dBColumn.setType("longtext");
				dBColumn.setLength("");
				break;
			default:
				if (length >= 2000) {
					dBColumn.setType("text");
					dBColumn.setLength("");
				} else {
					dBColumn.setType("varchar");
					dBColumn.setLength("(" + (length == 0 ? DEFAULT_LENGTH_STRING : length) + ")");
				}
			}
			if (StringUtils.isNotBlank(defaultValue)) {
				dBColumn.setDefaultValue("\"" + defaultValue + "\"");
			}
			break;
		default:
			// 关联关系
			String ralation = entityColumn.relation();
			dBColumn = this.getRalationDBColumn(field, ralation);
		}

		return dBColumn;
	}

	// 获取列
	private DBColumn getDefaultDBColumn(Field field) {

		DBColumn dBColumn = new DBColumn();

		String fieldClassName = field.getType().getName();

		// 列类型
		switch (fieldClassName) {
		case "java.lang.Integer":
		case "int":
			dBColumn.setType("integer");
			dBColumn.setLength("(" + DEFAULT_LENGTH_INTEGER + ")");
			break;
		case "java.lang.Long":
		case "long":
			dBColumn.setType("integer");
			dBColumn.setLength("(" + DEFAULT_LENGTH_LONG + ")");
			break;
		case "java.lang.Double":
		case "double":
			dBColumn.setType("double");
			dBColumn.setLength("(" + DEFAULT_LENGTH_DOUBLE_PRECISION + "," + DEFAULT_LENGTH_DOUBLE_SCALE + ")");
			break;
		case "java.util.Date":
			dBColumn.setType("timestamp");
			break;
		case "java.lang.String":
			dBColumn.setType("varchar");
			dBColumn.setLength("(" + DEFAULT_LENGTH_STRING + ")");
			break;
		default:
			dBColumn = this.getRalationDBColumn(field, "id");
		}

		return dBColumn;
	}

	// 获取列
	private DBColumn getRalationDBColumn(Field field, String ralation) {

		DBColumn dBColumn = new DBColumn();

		// 实体类的属性
		Class<?> ralationClass = field.getType();
		List<Field> ralationFields = CreatorUtils.Attribute.getFields(ralationClass);
		Field ralationField = null;
		for (Field field2 : ralationFields) {
			if (StringUtils.equals(ralation, field2.getName())) {
				ralationField = field2;
				break;
			}
		}
		if (ralationField == null) {
			throw new RuntimeException("can not found field " + ralation + " in class " + ralationClass.getName());
		}
		if (ralationField.getType() != String.class) {
			throw new RuntimeException("ralation type must be string.");
		}
		EntityColumn ralationEntityColumn = ralationField.getAnnotation(EntityColumn.class);
		if (ralationEntityColumn == null || ralationEntityColumn.length() == 0) {
			// 没有设置,默认
			dBColumn.setType("varchar");
			dBColumn.setLength("(" + DEFAULT_LENGTH_STRING + ")");
		} else {
			dBColumn.setType("varchar");
			dBColumn.setLength("(" + ralationEntityColumn.length() + ")");
		}

		return dBColumn;
	}

}
