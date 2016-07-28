package com.github.hualuomoli.commons.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 数值工具
 * @author hualuomoli
 *
 */
public class NumberUtils {

	// 小数点左移,原始数据缩小
	public static final String shiftLeft(String str, int length) {

		String ret = "";

		// 前补零
		StringBuilder buffer = new StringBuilder(str);
		for (int i = 0; i < length; i++) {
			buffer.insert(0, "0");
		}

		// 补零后的数据
		str = buffer.toString();
		// 小数点的位置
		int dot = str.indexOf(".");
		if (dot == -1) {
			// 没有小数点
			ret = str.substring(0, str.length() - length) + "." + str.substring(str.length() - length);
		} else {
			// 有小数点
			ret = str.substring(0, dot - length) + "." + str.substring(dot - length, dot) + str.substring(dot + 1);
		}

		return trimZero(ret);
	}

	// 小数点右移,原始数据缩放大
	public static final String shiftRight(String str, int length) {

		String ret = "";

		// 后补零
		for (int i = 0; i < length; i++) {
			str += "0";
		}

		// 小数点的位置
		int dot = str.indexOf(".");
		if (dot == -1) {
			// 没有小数点
			ret = str;
		} else if (dot == str.length() - length) {
			// 正好没有小数点
			ret = str.substring(0, dot) + str.substring(dot + 1);
		} else {
			// 有小数点
			ret = str.substring(0, dot) + str.substring(dot + 1, dot + length + 1) + "." + str.substring(dot + length + 1);
		}

		return trimZero(ret);
	}

	// 去除零
	public static final String trimZero(String str) {
		return trimRightZero(trimLeftZero(str));
	}

	// 去除左侧的零
	public static final String trimLeftZero(String str) {
		int index = -1;
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 1);
			if (StringUtils.equals(temp, ".")) {
				// 小数点
				index = i - 1;
				break;
			} else if (!StringUtils.equals(temp, "0")) {
				// 非零
				index = i;
				break;
			}
		}
		if (index == -1) {
			// 全部都是0
			return "0";
		} else {
			return str.substring(index);
		}
	}

	// 去除右侧的零
	public static final String trimRightZero(String str) {

		if (str.indexOf(".") == -1) {
			// 没有小数点
			return str;
		}

		int index = -1;
		for (int i = str.length() - 1; i >= 0; i--) {
			String temp = str.substring(i, i + 1);
			if (StringUtils.equals(temp, ".")) {
				// 小数点
				index = i;
				break;
			} else if (!StringUtils.equals(temp, "0")) {
				// 非零
				index = i + 1;
				break;
			}
		}
		if (index == -1) {
			// 全部都是0
			return "0";
		} else {
			return str.substring(0, index);
		}
	}

}
