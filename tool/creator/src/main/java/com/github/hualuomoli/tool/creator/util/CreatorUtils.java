package com.github.hualuomoli.tool.creator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnCustom;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;
import com.github.hualuomoli.base.entity.CommonField;
import com.github.hualuomoli.tool.creator.entity.CreatorColumn;
import com.github.hualuomoli.tool.creator.entity.CreatorColumnQuery;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.github.hualuomoli.tool.creator.util.CreatorUtils.Tool.Comparator;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 实体类属性工具
 * @author hualuomoli
 *
 */
public class CreatorUtils {

	private static final Logger logger = LoggerFactory.getLogger(CreatorUtils.class);

	private static String prefix = "";

	public static void setPrefix(String prefix) {
		CreatorUtils.prefix = prefix;
	}

	public static CreatorTable getCreatorTable(Class<?> entityCls) {
		EntityTable annotation = entityCls.getAnnotation(EntityTable.class);

		CreatorTable creatorTable = new CreatorTable();

		if (CommonField.class.isAssignableFrom(entityCls)) {
			creatorTable.setEntityType(CreatorTable.ENTITY_TYPE_COMMON);
		} else if (BaseField.class.isAssignableFrom(entityCls)) {
			creatorTable.setEntityType(CreatorTable.ENTITY_TYPE_BASE);
		} else {
			throw new RuntimeException("实体类必须继承 BaseField");
		}

		String dbName;
		String comments;
		List<CreatorColumn> columns;

		if (annotation == null) {
			dbName = Tool.unCamel(prefix + entityCls.getSimpleName());
			comments = "";
			columns = Attribute.getAttributes(entityCls, new HashSet<String>());
		} else {
			dbName = annotation.name();
			if (StringUtils.isBlank(dbName)) {
				dbName = Tool.unCamel(prefix + entityCls.getSimpleName());
			}
			comments = annotation.comment();
			columns = Attribute.getAttributes(entityCls, Sets.newHashSet(annotation.ignores()));
		}

		creatorTable.setDbName(dbName);
		creatorTable.setComments(comments);
		creatorTable.setColumns(columns);

		creatorTable.setQueryColumns(Query.getQueryAttributes(creatorTable.getColumns()));

		return creatorTable;
	}

	private static class Query {
		// 查询属性
		private static List<CreatorColumnQuery> getQueryAttributes(List<CreatorColumn> columns) {
			List<CreatorColumnQuery> queryColumns = loadQueryAttributes(columns);
			configQueryAttributes(queryColumns);
			return queryColumns;
		}

		// 配置属性
		private static void configQueryAttributes(List<CreatorColumnQuery> attributes) {

			// set fill db name
			int nameMax = Tool.getMaxLength(attributes, new Comparator<CreatorColumnQuery>() {
				@Override
				public int getCompareLength(CreatorColumnQuery attribute) {
					return attribute.getJavaNameLength() + (attribute.isEntity() ? attribute.getRelation().length() + 1 : 0);
				}
			});
			int dbNameMax = Tool.getMaxLength(attributes, new Comparator<CreatorColumnQuery>() {
				@Override
				public int getCompareLength(CreatorColumnQuery attribute) {
					return attribute.getDbNameLength();
				}
			});

			for (CreatorColumnQuery attribute : attributes) {
				String javaBlanks = Tool.getBlank(attribute.getJavaNameLength(), nameMax);
				String dbBlanks = Tool.getBlank(attribute.getDbNameLength(), dbNameMax);
				attribute.setJavaBlanks(javaBlanks);
				attribute.setDbBlanks(dbBlanks);
			}

		}

		// 查询属性
		private static List<CreatorColumnQuery> loadQueryAttributes(List<CreatorColumn> columns) {
			List<CreatorColumnQuery> queryColumns = Lists.newArrayList();

			for (CreatorColumn creatorColumn : columns) {
				Field field = creatorColumn.getField();
				EntityColumnQuery entityQuery = field.getAnnotation(EntityColumnQuery.class);
				if (entityQuery == null) {
					continue;
				}

				switch (field.getType().getName()) {
				case "java.lang.Integer":
				case "int":
					queryColumns.addAll(getCompareColumnQuery(creatorColumn, entityQuery));
					queryColumns.addAll(getArrayColumnQuery(creatorColumn, entityQuery));
					break;
				case "java.lang.Long":
				case "long":
					queryColumns.addAll(getCompareColumnQuery(creatorColumn, entityQuery));
					queryColumns.addAll(getArrayColumnQuery(creatorColumn, entityQuery));
					break;
				case "java.lang.Double":
				case "double":
					queryColumns.addAll(getCompareColumnQuery(creatorColumn, entityQuery));
					queryColumns.addAll(getArrayColumnQuery(creatorColumn, entityQuery));
					break;
				case "java.lang.String":
					queryColumns.addAll(getLikeColumnQuery(creatorColumn, entityQuery));
					queryColumns.addAll(getArrayColumnQuery(creatorColumn, entityQuery));
					break;
				case "java.util.Date":
					queryColumns.addAll(getCompareColumnQuery(creatorColumn, entityQuery));
					break;
				default:
					if (creatorColumn.isEntity()) {
						// entity
						queryColumns.addAll(getEntityColumnQuery(creatorColumn, entityQuery));
					} else {
						throw new RuntimeException("type " + field.getType().getName());
					}
				}
			}

			return queryColumns;
		}

		// 比较
		private static List<CreatorColumnQuery> getCompareColumnQuery(CreatorColumn creatorColumn, EntityColumnQuery entityQuery) {
			List<CreatorColumnQuery> compareQueryColumns = Lists.newArrayList();

			// 大于
			if (entityQuery.greaterThan()) {
				CreatorColumnQuery greaterThanColumnQuery = clone(creatorColumn);
				greaterThanColumnQuery.setOperator("<![CDATA[>]]>");
				greaterThanColumnQuery.setJavaName(greaterThanColumnQuery.getJavaName() + "GreaterThan");
				greaterThanColumnQuery.setComment(greaterThanColumnQuery.getComment() + " - 大于");
				compareQueryColumns.add(greaterThanColumnQuery);
			}
			// 大于等于
			if (entityQuery.greaterEqual()) {
				CreatorColumnQuery greaterEqualColumnQuery = clone(creatorColumn);
				greaterEqualColumnQuery.setOperator("<![CDATA[>=]]>");
				greaterEqualColumnQuery.setJavaName(greaterEqualColumnQuery.getJavaName() + "GreaterEqual");
				greaterEqualColumnQuery.setComment(greaterEqualColumnQuery.getComment() + " - 大于等于");
				compareQueryColumns.add(greaterEqualColumnQuery);
			}
			// 小于
			if (entityQuery.lessThan()) {
				CreatorColumnQuery lessThanColumnQuery = clone(creatorColumn);
				lessThanColumnQuery.setOperator("<![CDATA[<]]>");
				lessThanColumnQuery.setJavaName(lessThanColumnQuery.getJavaName() + "LessThan");
				lessThanColumnQuery.setComment(lessThanColumnQuery.getComment() + " - 小于");
				compareQueryColumns.add(lessThanColumnQuery);
			}
			// 小于等于
			if (entityQuery.lessEqual()) {
				CreatorColumnQuery lessEqualColumnQuery = clone(creatorColumn);
				lessEqualColumnQuery.setOperator("<![CDATA[<=]]>");
				lessEqualColumnQuery.setJavaName(lessEqualColumnQuery.getJavaName() + "LessEqual");
				lessEqualColumnQuery.setComment(lessEqualColumnQuery.getComment() + " - 小于等于");
				compareQueryColumns.add(lessEqualColumnQuery);
			}

			return compareQueryColumns;
		}

		// like
		private static List<CreatorColumnQuery> getLikeColumnQuery(CreatorColumn creatorColumn, EntityColumnQuery entityQuery) {
			List<CreatorColumnQuery> likeQueryColumns = Lists.newArrayList();

			// 左like
			if (entityQuery.leftLike()) {
				CreatorColumnQuery leftLikeColumnQuery = clone(creatorColumn);
				leftLikeColumnQuery.setOperator("like");
				leftLikeColumnQuery.setLike(true);
				leftLikeColumnQuery.setJavaName(leftLikeColumnQuery.getJavaName() + "LeftLike");
				leftLikeColumnQuery.setComment(leftLikeColumnQuery.getComment() + " - 左like");
				leftLikeColumnQuery.setLikeJavaName(leftLikeColumnQuery.getJavaName() + " + \"%\"");
				likeQueryColumns.add(leftLikeColumnQuery);
			}

			// 右like
			if (entityQuery.rightLike()) {
				CreatorColumnQuery rightLikeColumnQuery = clone(creatorColumn);
				rightLikeColumnQuery.setOperator("like");
				rightLikeColumnQuery.setLike(true);
				rightLikeColumnQuery.setJavaName(rightLikeColumnQuery.getJavaName() + "RightLike");
				rightLikeColumnQuery.setComment(rightLikeColumnQuery.getComment() + " - 右like");
				rightLikeColumnQuery.setLikeJavaName("\"%\" + " + rightLikeColumnQuery.getJavaName());
				likeQueryColumns.add(rightLikeColumnQuery);
			}

			// 左右like
			if (entityQuery.bothLike()) {
				CreatorColumnQuery bothLikeColumnQuery = clone(creatorColumn);
				bothLikeColumnQuery.setOperator("like");
				bothLikeColumnQuery.setLike(true);
				bothLikeColumnQuery.setJavaName(bothLikeColumnQuery.getJavaName() + "BothLike");
				bothLikeColumnQuery.setComment(bothLikeColumnQuery.getComment() + " - 左右like");
				bothLikeColumnQuery.setLikeJavaName("\"%\" + " + bothLikeColumnQuery.getJavaName() + " + \"%\"");
				likeQueryColumns.add(bothLikeColumnQuery);
			}

			return likeQueryColumns;
		}

		// 数组
		private static List<CreatorColumnQuery> getArrayColumnQuery(CreatorColumn creatorColumn, EntityColumnQuery entityQuery) {
			List<CreatorColumnQuery> arrayQueryColumns = Lists.newArrayList();

			// array
			if (entityQuery.inArray()) {
				CreatorColumnQuery columnQuery = clone(creatorColumn);
				columnQuery.setArray(true);
				columnQuery.setJavaName(columnQuery.getJavaName() + "Array");
				columnQuery.setComment(columnQuery.getComment() + " - 查询数组");
				columnQuery.setJavaTypeName(columnQuery.getJavaTypeName() + "[]");
				arrayQueryColumns.add(columnQuery);
			}

			return arrayQueryColumns;
		}

		// 实体类
		private static List<CreatorColumnQuery> getEntityColumnQuery(CreatorColumn creatorColumn, EntityColumnQuery entityQuery) {
			List<CreatorColumnQuery> entityQueryColumns = Lists.newArrayList();

			Field field = creatorColumn.getField();

			// entity
			if (entityQuery.inArray()) {
				CreatorColumnQuery columnQuery = clone(creatorColumn);
				columnQuery.setArray(true);
				columnQuery.setJavaName(columnQuery.getJavaName() + "Array");
				columnQuery.setComment(columnQuery.getComment() + " - 查询数组");
				// 设置类型
				List<Field> fs = Attribute.getFields(field.getType());
				String relation = creatorColumn.getRelation();
				for (Field f : fs) {
					if (StringUtils.equals(relation, f.getName())) {
						columnQuery.setJavaTypeName(f.getType().getName() + "[]");
						break;
					}
				}
				entityQueryColumns.add(columnQuery);
			}

			return entityQueryColumns;
		}

		private static CreatorColumnQuery clone(CreatorColumn creatorColumn) {
			CreatorColumnQuery columnQuery = new CreatorColumnQuery();

			columnQuery.setField(creatorColumn.getField());
			columnQuery.setJavaTypeName(creatorColumn.getJavaTypeName());
			columnQuery.setJavaName(creatorColumn.getJavaName());
			columnQuery.setDbName(creatorColumn.getDbName());

			columnQuery.setComment(creatorColumn.getComment());
			columnQuery.setString(creatorColumn.isString());
			columnQuery.setEntity(creatorColumn.isEntity());
			columnQuery.setRelation(creatorColumn.getRelation());

			return columnQuery;
		}

	}

	public static class Attribute {

		// 获取属性集合
		private static List<CreatorColumn> getAttributes(Class<?> entityCls, Set<String> ignores) {
			List<CreatorColumn> attributes = loadAttributes(entityCls, ignores);
			configAttributes(attributes);

			// sort
			Collections.sort(attributes, new java.util.Comparator<CreatorColumn>() {

				@Override
				public int compare(CreatorColumn o1, CreatorColumn o2) {

					// 包含o1,返回-1
					if (o1.getJavaName().equals("id")) {
						return -1;
					}
					// 包含o2,返回1
					if (o2.getJavaName().equals("id")) {
						return 1;
					}

					return 0;
				}
			});

			return attributes;
		}

		// 配置属性
		private static void configAttributes(List<CreatorColumn> attributes) {

			// set fill db name
			int nameMax = Tool.getMaxLength(attributes, new Comparator<CreatorColumn>() {
				@Override
				public int getCompareLength(CreatorColumn attribute) {
					return attribute.getJavaNameLength() + (attribute.isEntity() ? attribute.getRelation().length() + 1 : 0);
				}
			});
			int dbNameMax = Tool.getMaxLength(attributes, new Comparator<CreatorColumn>() {
				@Override
				public int getCompareLength(CreatorColumn attribute) {
					return attribute.getDbNameLength();
				}
			});

			for (CreatorColumn attribute : attributes) {
				String javaBlanks = Tool.getBlank(attribute.getJavaNameLength() + (attribute.isEntity() ? attribute.getRelation().length() + 1 : 0), nameMax);
				String dbBlanks = Tool.getBlank(attribute.getDbNameLength(), dbNameMax);
				attribute.setJavaBlanks(javaBlanks);
				attribute.setDbBlanks(dbBlanks);
			}

		}

		// 加载属性
		private static List<CreatorColumn> loadAttributes(Class<?> entityCls, Set<String> ignores) {

			List<CreatorColumn> attributes = Lists.newArrayList();

			if (entityCls == null) {
				return attributes;
			}

			List<Field> fields = getFields(entityCls);
			for (Field field : fields) {
				String javaName = field.getName();

				// ignore
				if (ignores.contains(javaName)) {
					continue;
				}

				// valid
				if (!valid(field, entityCls)) {
					continue;
				}

				// List<Bean> beans
				// 一对多
				if (Collection.class.isAssignableFrom(field.getType())) {
					continue;
				}

				CreatorColumn attribute = new CreatorColumn();
				attribute.setField(field);
				attribute.setJavaTypeName(field.getType().getName());
				// java
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());

				String dbName; // 数据库名

				EntityColumn annotation = field.getAnnotation(EntityColumn.class);

				if (annotation == null || StringUtils.isBlank(annotation.name())) {
					dbName = Tool.unCamel(javaName);
				} else {
					dbName = annotation.name();
				}

				// 一对一
				if (BaseField.class.isAssignableFrom(field.getType())) {
					// 实体类
					String relation = annotation == null ? "id" : annotation.relation();
					attribute.setRelation(relation);
					// 数据库列
					if (annotation == null || StringUtils.isBlank(annotation.name())) {
						// dbName = ? field.getName() +relation:"";
						dbName = Tool.unCamel(javaName + StringUtils.capitalize(relation));
					} else {
						dbName = annotation.name();
					}
					attribute.setDbName(dbName);
					attribute.setDbNameLength(attribute.getDbName().length());
					// 设置为实体类
					attribute.setEntity(true);
				} else if (field.getType() == java.lang.String.class) {
					// 字符串
					// 数据库列
					attribute.setDbName(dbName);
					attribute.setDbNameLength(attribute.getDbName().length());
					// 设置为字符串
					attribute.setString(true);
				} else if (field.getType().getName().startsWith("java.lang")//
						|| field.getType().getName().equals("java.util.Date")) {
					// 普通类型
					// 数据库列
					attribute.setDbName(dbName);
					attribute.setDbNameLength(attribute.getDbName().length());
				} else if (field.getType().getSimpleName().equals("byte[]")) {
					// 普通类型
					// 数据库列
					attribute.setDbName(dbName);
					attribute.setDbNameLength(attribute.getDbName().length());
				} else {
					EntityColumnCustom custom = field.getAnnotation(EntityColumnCustom.class);
					if (custom != null) {
						// number
						attribute.setDbName(dbName);
						attribute.setDbNameLength(attribute.getDbName().length());
						attribute.setTypeHandler(custom.typeHander().getName());
						attribute.setCustom(true);
					} else {
						logger.warn("无法识别的类型 {} {}", field.getName(), field.getType().getName());
						throw new RuntimeException("can not support type " + field.getType().getName());
					}
				}
				attribute.setComment(annotation == null ? "" : annotation.comment());

				// 添加
				attributes.add(attribute);
			}

			return attributes;

		}

		// 获取属性
		public static List<Field> getFields(Class<?> entityCls) {
			List<Field> list = _getFields(entityCls);
			List<Field> fields = Lists.newArrayList();
			Set<String> names = Sets.newHashSet();
			for (Field field : list) {
				if (names.contains(field.getName())) {
					continue;
				}
				names.add(field.getName());
				fields.add(field);
			}
			return fields;
		}

		// 获取属性
		private static List<Field> _getFields(Class<?> entityCls) {
			List<Field> list = Lists.newArrayList();
			if (entityCls == null) {
				return list;
			}

			// 当前类的属性
			Field[] fields = entityCls.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}

			// 父类属性
			list.addAll(_getFields(entityCls.getSuperclass()));

			return list;
		}

		// 是否是合法的属性
		private static boolean valid(Field field, Class<?> cls) {

			int mod = field.getModifiers();

			// must private
			if (!Modifier.isPrivate(mod) && !Modifier.isProtected(mod)) {
				return false;
			}

			// ignore static final
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				return false;
			}

			String name = field.getName();
			String upperName = name.substring(0, 1).toUpperCase() + (name.length() > 1 ? name.substring(1) : "");
			Class<?> type = field.getType();

			try {
				Method setMethod = cls.getMethod("set" + upperName, type);
				if (setMethod == null || setMethod.getModifiers() != Modifier.PUBLIC || setMethod.getReturnType() != void.class) {
					return false;
				}

				Method getMethod = cls.getMethod("get" + upperName);

				if (getMethod == null || getMethod.getModifiers() != Modifier.PUBLIC
						|| !StringUtils.equals(getMethod.getReturnType().getName(), type.getName())) {
					return false;
				}

				return true;
			} catch (Exception e) {
				return false;
			}

		}

	}

	// 工具
	public static class Tool {

		// 比较器
		public interface Comparator<T> {

			// Object's length greater max
			int getCompareLength(T t);

		}

		// 获取集合中最大的长度
		static <T> int getMaxLength(Collection<T> datas, Comparator<T> checker) {
			if (datas == null || datas.size() == 0 || checker == null) {
				return 0;
			}
			int max = -1;
			for (T t : datas) {
				int length = checker.getCompareLength(t);
				if (length > max) {
					max = length;
				}
			}
			return max;
		}

		// 获取空白数据(总长度 - 数据长度)
		static String getBlank(int dataLength, int max) {
			int t = max - dataLength;
			String blanks = "";
			for (int i = 0; i < t; i++) {
				blanks += " ";
			}
			return blanks;
		}

		// 逆驼峰命名法
		static String unCamel(String str) {
			StringBuilder buffer = new StringBuilder();
			char[] array = StringUtils.uncapitalize(str).toCharArray();
			for (char c : array) {
				if (c >= 'A' && c <= 'Z') {
					buffer.append("_");
				}
				buffer.append(String.valueOf(c).toLowerCase());
			}
			return buffer.toString();
		}

	}

}
