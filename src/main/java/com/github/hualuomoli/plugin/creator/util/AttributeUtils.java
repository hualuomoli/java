package com.github.hualuomoli.plugin.creator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.annotation.Ignore;
import com.github.hualuomoli.plugin.creator._Attribute;
import com.google.common.collect.Lists;

/**
 * 实体类属性工具
 * @author hualuomoli
 *
 */
public class AttributeUtils extends CreatorUtils {

	/**
	 * 获取属性集合
	 * @param entityCls 实体类class
	 * @param ignoreAttributes 忽略的属性名
	 * @param projectPackageName 项目包名,如com.github.hualuomoli.demo
	 * @return 属性集合
	 */
	public static List<_Attribute> getAttributes(Class<?> entityCls, String projectPackageName) {
		List<_Attribute> attributes = loadAttributes(entityCls, projectPackageName);
		configAttributes(attributes);

		// sort
		Collections.sort(attributes, new Comparator<_Attribute>() {

			@Override
			public int compare(_Attribute o1, _Attribute o2) {
				// 如果比较者是id,改变位置
				if (StringUtils.equals(o1.getJavaName(), "id")) {
					return -1;
				}
				// 如果被比较者是id,不改变位置
				if (StringUtils.equals(o2.getJavaName(), "id")) {
					return 1;
				}
				// 如果比较者是version,改变位置
				if (StringUtils.equals(o1.getJavaName(), "version")) {
					return -1;
				}
				return 0;
			}
		});

		return attributes;
	}

	/**
	 * 配置属性
	 * @param attributes 属性集合
	 */
	private static void configAttributes(List<_Attribute> attributes) {

		// set fill db name
		int nameMax = getMaxLength(attributes, new Checker<_Attribute>() {
			@Override
			public int getCompareLength(_Attribute attribute) {
				return attribute.getJavaNameLength();
			}
		});
		int dbNameMax = getMaxLength(attributes, new Checker<_Attribute>() {
			@Override
			public int getCompareLength(_Attribute attribute) {
				return attribute.getDbNameLength();
			}
		});

		for (_Attribute attribute : attributes) {
			String javaBlanks = getBlank(attribute.getJavaNameLength(), nameMax);
			String dbBlanks = getBlank(attribute.getDbNameLength(), dbNameMax);
			attribute.setJavaBlanks(javaBlanks);
			attribute.setDbBlanks(dbBlanks);
		}

	}

	/**
	 * 获取类的属性
	 * @param entityCls class
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 是否有效
	 */
	private static List<_Attribute> loadAttributes(Class<?> entityCls, String projectPackageName) {

		List<_Attribute> attributes = Lists.newArrayList();

		if (entityCls == null) {
			return attributes;
		}

		Field[] fields = entityCls.getDeclaredFields();
		for (Field field : fields) {
			_Attribute attribute = new _Attribute();
			attribute.setField(field);
			// ignore
			Ignore ignore = field.getAnnotation(Ignore.class);
			if (ignore != null) {
				continue;
			}
			// valid
			if (!valid(field, entityCls)) {
				continue;
			}
			// List<Bean> beans
			// 一对多
			if (field.getType() == java.util.List.class || field.getType() == java.util.ArrayList.class //
					|| field.getType() == java.util.Set.class || field.getType() == java.util.HashSet.class //
					|| field.getType().getName().equals("java.lang.String[]")//
			) {
				continue;
			}

			String javaName = field.getName(); // java名
			String dbName = unCamel(javaName); // 数据库名

			// 一对一
			if (StringUtils.startsWith(field.getType().getName(), projectPackageName)) {
				// 实体类

				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length() + 3); // 实体类长度加3 user.id

				// 数据库列
				attribute.setDbName(dbName);
				attribute.setDbNameLength(attribute.getDbName().length() + 3);

				// 设置为实体类
				attribute.setEntity(true);
			} else if (field.getType() == java.lang.String.class) {
				// 普通类型

				// 实体类
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());

				// 数据库列
				attribute.setDbName(unCamel(dbName));
				attribute.setDbNameLength(attribute.getDbName().length());

				// 设置为字符串
				attribute.setString(true);
			} else {
				// 普通类型

				// 实体类
				attribute.setJavaName(javaName);
				attribute.setJavaNameLength(javaName.length());

				// 数据库列
				attribute.setDbName(unCamel(dbName));
				attribute.setDbNameLength(attribute.getDbName().length());
			}

			// 添加
			attributes.add(attribute);
		}

		attributes.addAll(loadAttributes(entityCls.getSuperclass(), projectPackageName));

		return attributes;

	}

	/**
	 * Field是否有效,getter和setter是否合法 ,属性是private的
	 * @param field Field
	 * @param cls class
	 * @return 是否合法
	 */
	private static boolean valid(Field field, Class<?> cls) {

		int mod = field.getModifiers();

		// must private
		if (!Modifier.isPrivate(mod) && !Modifier.isProtected(mod)) {
			return false;
		}

		// ignore static final
		if (Modifier.isStatic(mod) && Modifier.isFinal(mod)) {
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

}
