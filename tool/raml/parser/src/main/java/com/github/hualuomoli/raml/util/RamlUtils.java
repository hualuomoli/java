package com.github.hualuomoli.raml.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	 * 数据是否有效
	 * @param data 数据
	 * @return 数据是否有效,不为空白
	 */
	private static boolean valid(String data) {
		return StringUtils.isNotBlank(data);
	}

	/**
	 * 首字母大写
	 * @param data 数据
	 * @return 首字母大写
	 */
	public static String cap(String data) {
		if (!valid(data)) {
			return data;
		}
		String temp = data.trim();
		return temp.substring(0, 1).toUpperCase() + temp.substring(1);
	}

	/**
	 * 首字母小写
	 * @param data 数据
	 * @return 首字母小写
	 */
	public static String unCap(String data) {
		if (!valid(data)) {
			return data;
		}
		String temp = data.trim();
		return temp.substring(0, 1).toLowerCase() + temp.substring(1);
	}

	/**
	 * 按照换行符分割
	 * @param data 数据
	 * @return 数据集合
	 */
	public static List<String> splitByLine(String data) {
		if (!valid(data)) {
			return Lists.newArrayList();
		}
		return Lists.newArrayList(data.split("\n"));
	}

	/**
	 * 删除换行符
	 * @param data 数据
	 * @return 数据集合
	 */
	public static String removeLine(String data) {
		if (!valid(data)) {
			return StringUtils.EMPTY;
		}
		return data.replaceAll("\n", "");
	}

	/**
	 * 去掉前缀,如果数据的前缀是prefix,则去除;否则返回原数据
	 * @param data 数据
	 * @param prefix 前缀
	 * @return 去掉前缀后的数据
	 */
	public static String removePrefix(String data, String prefix) {
		if (!valid(data) || !valid(prefix)) {
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
	 * 转义双引号 "data" --> \"data\"
	 * @param data 数据
	 * @return 转义后的数据
	 */
	public static String escapeQuotes(String data) {
		if (!valid(data)) {
			return StringUtils.EMPTY;
		}
		return data.replaceAll("\"", "\\\\\"");
	}

	/**
	 * 处理example 去掉换行,双引号转义
	 * @param example 例子
	 * @return 处理后的例子
	 */
	public static String dealExample(String example) {
		return removeLine(escapeQuotes(example));
	}

	/**
	* 处理description 去掉换行
	* @param description 描述
	* @return 处理后description
	*/
	public static String dealDescription(String description) {
		return removeLine(description);
	}

	/**
	* 处理displayName 去掉换行
	* @param displayName 显示名称
	* @return 处理后displayName
	*/
	public static String dealDisplayName(String displayName) {
		return removeLine(displayName);
	}

	/**
	 * 去掉URI中参数部分 /user/{username}/{addressid} --> /user
	 * @param uri URI
	 * @return 去掉URI中参数部分
	 */
	public static String removeUriParam(String uri) {
		if (!valid(uri)) {
			return StringUtils.EMPTY;
		}
		return uri.replaceAll("/\\{.*}", "");
	}

	/**
	 * 获取URI最后一个名称 /user/order/product --> product
	 * @param parentFullUri URI
	 * @return 最后一个URI名称
	 */
	public static String getLastUri(String parentFullUri) {
		String uri = removeUriParam(parentFullUri);
		if (!valid(uri)) {
			return StringUtils.EMPTY;
		}
		String[] array = uri.split("/");
		String name = array[array.length - 1];
		return name;
	}

	/**
	 * 获取功能的全路径
	 * @param action 功能
	 * @return 功能的全路径
	 */
	public static String getFullUri(Action action) {
		return getFullUri(action.getResource());
	}

	/**
	 * 获取资源的全路径
	 * @param resource 资源
	 * @return 资源的全路径
	 */
	public static String getFullUri(Resource resource) {
		if (resource == null) {
			return StringUtils.EMPTY;
		}
		String relativeUri = resource.getRelativeUri();
		if (StringUtils.isBlank(relativeUri)) {
			return StringUtils.EMPTY;
		}
		return getFullUri(resource.getParentResource()) + relativeUri;
	}

	/**
	 * 获取功能的全路径
	 * @param action 功能
	 * @return 功能的全路径
	 */
	public static Map<String, UriParameter> getFullUriParameter(Action action) {
		return getFullUriParameter(action.getResource());
	}

	/**
	 * 获取资源的全路径
	 * @param resource 资源
	 * @return 资源的全路径
	 */
	public static Map<String, UriParameter> getFullUriParameter(Resource resource) {
		if (resource == null) {
			return Maps.newHashMap();
		}
		// this
		Map<String, UriParameter> map = resource.getUriParameters();
		if (map == null) {
			return Maps.newHashMap();
		}
		// parent
		Map<String, UriParameter> parentMap = getFullUriParameter(resource.getParentResource());

		// full
		Map<String, UriParameter> fullMap = Maps.newHashMap();
		fullMap.putAll(map);
		fullMap.putAll(parentMap);

		return fullMap;
	}

}
