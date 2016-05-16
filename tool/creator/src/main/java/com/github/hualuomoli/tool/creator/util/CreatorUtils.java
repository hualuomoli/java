package com.github.hualuomoli.tool.creator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityIgnore;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.tool.creator.entity.CreatorColumn;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 实体类属性工具
 * @author hualuomoli
 *
 */
public class CreatorUtils {

	private static final Logger logger = LoggerFactory.getLogger(CreatorUtils.class);

	public static CreatorTable getCreatorTable(Class<?> entityCls) {
		EntityTable annotation = entityCls.getAnnotation(EntityTable.class);

		if (annotation == null) {
			throw new RuntimeException("请在类上增加 @EntityTable");
		}

		CreatorTable creatorTable = new CreatorTable();

		creatorTable.setDbName(Tool.unCamel(StringUtils.isBlank(annotation.name()) ? entityCls.getSimpleName() : annotation.name()));
		creatorTable.setComments(StringUtils.isBlank(annotation.comment()) ? "表" : annotation.comment());
		creatorTable.setColumns(getAttributes(entityCls));

		return creatorTable;
	}

	// 获取属性集合
	private static List<CreatorColumn> getAttributes(Class<?> entityCls) {
		List<CreatorColumn> attributes = loadAttributes(entityCls);
		configAttributes(attributes);

		final Map<String, Integer> beginMap = Maps.newHashMap();
		beginMap.put("id", 1);
		beginMap.put("version", 2);
		final Map<String, Integer> endMap = Maps.newHashMap();
		endMap.put("createBy", 1);
		endMap.put("createDate", 2);
		endMap.put("updateBy", 3);
		endMap.put("updateDate", 4);
		endMap.put("status", 5);

		// sort
		Collections.sort(attributes, new java.util.Comparator<CreatorColumn>() {

			@Override
			public int compare(CreatorColumn o1, CreatorColumn o2) {

				/////////////////////////////////////////////
				// 两者都包含,返回他们的位置
				if (beginMap.containsKey(o1.getJavaName()) && beginMap.containsKey(o2.getJavaName())) {
					return beginMap.get(o1.getJavaName()) - beginMap.get(o2.getJavaName());
				}

				// 包含o1,返回-1
				if (beginMap.containsKey(o1.getJavaName())) {
					return -1;
				}
				// 包含o2,返回1
				if (beginMap.containsKey(o2.getJavaName())) {
					return 1;
				}

				/////////////////////////////////////////////
				// 两者都包含,返回他们的位置
				if (endMap.containsKey(o1.getJavaName()) && endMap.containsKey(o2.getJavaName())) {
					return endMap.get(o1.getJavaName()) - endMap.get(o2.getJavaName());
				}

				// 包含o1,返回1
				if (endMap.containsKey(o1.getJavaName())) {
					return 1;
				}
				// 包含o2,返回-1
				if (endMap.containsKey(o2.getJavaName())) {
					return -1;
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
			String javaBlanks = Tool.getBlank(attribute.getJavaNameLength(), nameMax);
			String dbBlanks = Tool.getBlank(attribute.getDbNameLength(), dbNameMax);
			attribute.setJavaBlanks(javaBlanks);
			attribute.setDbBlanks(dbBlanks);
		}

	}

	// 加载属性
	private static List<CreatorColumn> loadAttributes(Class<?> entityCls) {

		List<CreatorColumn> attributes = Lists.newArrayList();

		if (entityCls == null) {
			return attributes;
		}

		List<Field> fields = getFields(entityCls);
		for (Field field : fields) {
			CreatorColumn attribute = new CreatorColumn();
			attribute.setField(field);
			// ignore
			EntityIgnore ignore = field.getAnnotation(EntityIgnore.class);
			if (ignore != null) {
				continue;
			}
			// valid
			if (!valid(field, entityCls)) {
				continue;
			}
			// List<Bean> beans
			// 一对多
			if (List.class.isAssignableFrom(field.getType())//
					|| Set.class.isAssignableFrom(field.getType())) {
				continue;
			}

			String javaName = field.getName(); // java名
			String dbName; // 数据库名

			EntityColumn annotation = field.getAnnotation(EntityColumn.class);
			if (annotation == null || StringUtils.isBlank(annotation.name())) {
				dbName = Tool.unCamel(javaName);
			} else {
				dbName = annotation.name();
			}

			// 一对一
			if (field.getType().getAnnotation(EntityTable.class) != null) {
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
				// java
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());
				// 设置为实体类
				attribute.setEntity(true);
			} else if (field.getType() == java.lang.String.class) {
				// 字符串
				// 数据库列
				attribute.setDbName(Tool.unCamel(dbName));
				attribute.setDbNameLength(attribute.getDbName().length());
				// java
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());
				// 设置为字符串
				attribute.setString(true);
			} else if (field.getType().getName().startsWith("java.lang")//
					|| field.getType().getName().startsWith("java.util")) {
				// 普通类型
				// 数据库列
				attribute.setDbName(Tool.unCamel(dbName));
				attribute.setDbNameLength(attribute.getDbName().length());
				// java
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());
			} else {
				logger.warn("请在 {} 上增加 @EntityTable", field.getName());
				throw new RuntimeException("can not support type " + field.getType().getName());
			}

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

			if (getMethod == null || getMethod.getModifiers() != Modifier.PUBLIC || !StringUtils.equals(getMethod.getReturnType().getName(), type.getName())) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 比较器
	private interface Comparator<T> {

		// Object's length greater max
		int getCompareLength(T t);

	}

	// 工具
	private static class Tool {

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
