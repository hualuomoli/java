package com.github.hualuomoli.plugin.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 简单实现
public final class Tool {

	protected static final Logger logger = LoggerFactory.getLogger(Tool.class);

	/**
	 * 获取真实的URL参数
	 * @param url URL地址
	 * @param parameters URL参数
	 * @return URL的真实地址
	 */
	public static String getRealUrl(String url, Object... parameters) {
		if (parameters == null || parameters.length == 0) {
			return url;
		}

		return _getUrl(url, 0, parameters);
	}

	// get
	private static String _getUrl(String url, int index, Object... parameters) {
		int start = url.indexOf("/{");
		int end = url.indexOf("}");

		if (start > 0 && end > start) {
			url = url.substring(0, start + 1) + parameters[index].toString() + url.substring(end + 1);
			return _getUrl(url, index + 1, parameters);
		}

		return url;
	}

}