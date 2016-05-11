package com.github.hualuomoli.tool.creator;

import java.util.Collection;

/**
 * 生成器工具
 * @author hualuomoli
 *
 */
public class CreatorUtils {

	/**
	 * 获取相对包名
	 * @param entityCls 实体类名
	 * @param skip 跳过的字符串
	 * @return 相对包名
	 */
	public static String getRelativePackageName(Class<?> entityCls, String skip) {
		String packageName = getPackageName(entityCls);
		String relativePackageName;
		// 去掉前缀
		if (skip != null && packageName.startsWith(skip)) {
			relativePackageName = packageName.substring(skip.length());
		} else {
			relativePackageName = packageName;
		}
		// 如果相对包名以逗号(,)开头,去掉
		if (relativePackageName.startsWith(".")) {
			relativePackageName = relativePackageName.substring(1);
		}
		// remove .entity.
		return relativePackageName;
	}

	/**
	 * 获取包名(不包含entity)
	 * @param entityCls 实体类名
	 * @return 包名
	 */
	public static String getPackageName(Class<?> entityCls) {
		String name = entityCls.getName();
		// remove .entity.
		return name.substring(0, name.indexOf(".entity."));
	}

	/**
	 * 获取最大长度
	 * @param datas 集合
	 * @param checker 验证器
	 * @return 集合中最大长度
	 */
	public static <T> int getMaxLength(Collection<T> datas, Checker<T> checker) {
		if (datas == null || datas.size() == 0 || checker == null) {
			return 0;
		}
		int max = -1;
		for (T t : datas) {
			String str = checker.getCompareString(t);
			if (str != null && str.length() > max) {
				max = str.length();
			}
		}
		return max;
	}

	public interface Checker<T> {

		// Object's length greater max
		String getCompareString(T t);

	}

	/**
	 * 获取空白数据(总长度 - 数据长度)
	 * @param str 数据
	 * @param max 总长度
	 * @return 空白数据
	 */
	public static String getBlank(String str, int max) {
		int t = max - str.length();
		String blanks = "";
		for (int i = 0; i < t; i++) {
			blanks += " ";
		}
		return blanks;
	}

	/**
	 * 首字符大写
	 * @param str 字符串
	 * @return 首字符大写字符串
	 */
	public static String cap(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 首字符小写
	 * @param str 字符串
	 * @return 首字符小写字符串
	 */
	public static String unCap(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * 逆驼峰命名法
	 * @param name 属性名称
	 * @return 逆驼峰名
	 */
	public static String unCamel(String str) {
		StringBuilder buffer = new StringBuilder();
		char[] array = unCap(str).toCharArray();
		for (char c : array) {
			if (c >= 'A' && c <= 'Z') {
				buffer.append("_");
			}
			buffer.append(String.valueOf(c).toLowerCase());
		}
		return buffer.toString();
	}

}
