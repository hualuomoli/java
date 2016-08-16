package com.github.hualuomoli.plugin.http.adaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.http.AsyncHttp;

// 简单实现
public abstract class AsyncHttpAdaptor implements AsyncHttp {

	protected static final Logger logger = LoggerFactory.getLogger(AsyncHttpAdaptor.class);

	/**
	 * 获取真实的URL参数
	 * @param url URL地址
	 * @param parameters URL参数
	 * @return URL的真实地址
	 */
	public String getRealUrl(String url, Object... parameters) {
		if (parameters == null || parameters.length == 0) {
			return url;
		}

		return this._getUrl(url, 0, parameters);
	}

	// get
	private String _getUrl(String url, int index, Object... parameters) {
		int start = url.indexOf("/{");
		int end = url.indexOf("}");

		if (start > 0 && end > start) {
			url = url.substring(0, start + 1) + parameters[index].toString() + url.substring(end + 1);
			return this._getUrl(url, index + 1, parameters);
		}

		return url;
	}

}