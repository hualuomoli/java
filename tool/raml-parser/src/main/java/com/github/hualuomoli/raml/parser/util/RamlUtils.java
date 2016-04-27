package com.github.hualuomoli.raml.parser.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * 工具
 * @author hualuomoli
 *
 */
public class RamlUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	public static String getCurrentTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(new Date());
	}

	/**
	 * 按照换行分割
	 * @param data 数据
	 * @return 数据集合
	 */
	public static List<String> splitByLine(String data) {
		List<String> lines = Lists.newArrayList();

		if (StringUtils.isEmpty(data)) {
			return lines;
		}
		String[] array = data.split("\n");
		for (String line : array) {
			lines.add(line);
		}
		return lines;
	}

	/**
	 * 替换双引号 "data" --> \"data\"
	 * @param data 数据
	 * @return 替换后的数据
	 */
	public static String replaceQuotes(String data) {
		if (StringUtils.isEmpty(data)) {
			return StringUtils.EMPTY;
		}
		return data.replaceAll("\"", "\\\\\"");
	}

	/**
	 * 去掉URI中参数部分 /user/{username}/{addressid} --> /user
	 * @param parentFullUri URI
	 * @return 去掉URI中参数部分
	 */
	public static String trimUriParam(String parentFullUri) {
		if (StringUtils.isEmpty(parentFullUri)) {
			return StringUtils.EMPTY;
		}
		return parentFullUri.replaceAll("/\\{.*}", "");
	}

	/**
	 * 去掉前缀
	 * @param data 数据
	 * @param prefix 前缀
	 * @return 去掉后的数据
	 */
	public static String trimPrefix(String data, String prefix) {

		if (StringUtils.isEmpty(data) || StringUtils.isEmpty(prefix)) {
			return data;
		}

		if (data.length() <= prefix.length()) {
			return data;
		}

		if (data.startsWith(prefix)) {
			return data.substring(prefix.length());
		}

		return data;

	}

	/**
	 * 获取URI最后一个名称 /user/order/product --> product
	 * @param parentFullUri
	 * @return 最后一个URI名称
	 */
	public static String getUriLastName(String parentFullUri) {
		String uriPath = trimUriParam(parentFullUri);
		if (StringUtils.isEmpty(uriPath)) {
			return StringUtils.EMPTY;
		}
		String[] array = uriPath.split("/");
		String name = array[array.length - 1];
		return name;
	}

}
