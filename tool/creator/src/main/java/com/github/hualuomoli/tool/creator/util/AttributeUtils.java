package com.github.hualuomoli.tool.creator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.tool.creator.entity.Attribute;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 实体类属性工具
 * @author hualuomoli
 *
 */
public class AttributeUtils extends CreatorUtils {

	/**
	 * 获取属性集合
	 * @param entityCls 实体类class
	 * @param ignores 忽略的属性名
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 属性集合
	 */
	public static List<Attribute> getAttributes(Class<?> entityCls, Set<String> ignores, String projectPackageName) {
		List<Attribute> attributes = loadAttributes(entityCls, ignores, projectPackageName);
		configAttributes(attributes);
		return attributes;
	}

	/**
	 * 配置属性
	 * @param attributes 属性集合
	 */
	private static void configAttributes(List<Attribute> attributes) {

		// set fill db name
		int nameMax = getMaxLength(attributes, new Checker<Attribute>() {
			@Override
			public int getCompareLength(Attribute attribute) {
				return attribute.getNameLength();
			}
		});
		int dbNameMax = getMaxLength(attributes, new Checker<Attribute>() {
			@Override
			public int getCompareLength(Attribute attribute) {
				return attribute.getDbNameLength();
			}
		});

		for (Attribute attribute : attributes) {
			String blanks = getBlank(attribute.getNameLength(), nameMax);
			String dbBlanks = getBlank(attribute.getDbNameLength(), dbNameMax);
			attribute.setBlanks(blanks);
			attribute.setDbBlanks(dbBlanks);
		}

	}

	/**
	 * 获取类的属性
	 * @param entityCls class
	 * @param ignores 忽略属性
	 * @param projectPackageName 项目包名,如com.github.hualuomoli
	 * @return 是否有效
	 */
	private static List<Attribute> loadAttributes(Class<?> entityCls, Set<String> ignores, String projectPackageName) {

		List<Attribute> attributes = Lists.newArrayList();

		if (ignores == null) {
			ignores = Sets.newHashSet();
		}
		if (entityCls == null) {
			return attributes;
		}

		Field[] fields = entityCls.getDeclaredFields();
		for (Field field : fields) {
			Attribute attribute = new Attribute();
			// ignore
			if (ignores.contains(field.getName())) {
				continue;
			}
			// valid
			if (!valid(field, entityCls)) {
				continue;
			}
			// List<Bean> beans
			// 一对多
			if (field.getType() == java.util.List.class || field.getType() == java.util.ArrayList.class) {
				continue;
			}
			// 一对一
			if (StringUtils.startsWith(field.getType().getName(), projectPackageName)) {
				String attributeName = field.getName();

				// 实体类
				attribute.setName(attributeName);
				attribute.setNameLength(attributeName.length() + 3); // 实体类长度加3 user.id

				// 数据库列
				attribute.setDbName(unCamel(attributeName));
				attribute.setDbNameLength(attribute.getDbName().length() + 3); // 数据库列长度加3 user_id

				// 其他
				attribute.setEntity(true);
			} else {
				// 普通类型
				String attributeName = field.getName();

				// 实体类
				attribute.setName(attributeName);
				attribute.setNameLength(attributeName.length());

				// 数据库列
				attribute.setDbName(unCamel(attributeName));
				attribute.setDbNameLength(attribute.getDbName().length());

				// 其他,是否是字符串
				attribute.setString(field.getType() == String.class);
			}

			attributes.add(attribute);
		}

		attributes.addAll(loadAttributes(entityCls.getSuperclass(), ignores, projectPackageName));

		return attributes;

	}

	/**
	 * Field是否有效,getter和setter是否合法 ,属性是private的
	 * @param field Field
	 * @param cls class
	 * @return 是否合法
	 */
	public static boolean valid(Field field, Class<?> cls) {

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

			Method getMethod = null;
			// 如果是boolean,判断是否是is方法
			if (StringUtils.equalsIgnoreCase(type.getSimpleName(), "boolean")) {
				try {
					getMethod = cls.getMethod("is" + upperName);
				} catch (Exception e) {
				}
			}
			if (getMethod == null) {
				getMethod = cls.getMethod("get" + upperName);
			}

			if (getMethod == null || getMethod.getModifiers() != Modifier.PUBLIC || !StringUtils.equals(getMethod.getReturnType().getName(), type.getName())) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
