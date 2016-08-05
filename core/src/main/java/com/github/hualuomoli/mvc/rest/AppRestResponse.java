package com.github.hualuomoli.mvc.rest;

import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.mvc.rest.RestResponse.Config;
import com.github.hualuomoli.mvc.rest.RestResponse.ErrorData;
import com.github.hualuomoli.util.JackJsonUtils;

/**
 * APP的REST风格返回
 * @author hualuomoli
 * @see RestResponse
 *
 */
public abstract class AppRestResponse {

	private static Config config = new RestResponse.Config(0, "code", "msg", "data", "datas", "total", "pageNumber", "pageSize", "dataList");

	private static RestResponse rest = null;

	private static RestResponse getInstance() {
		if (rest == null) {
			synchronized (config) {
				if (rest == null) {
					rest = new RestResponse() {

						@Override
						public Config getConfig() {
							return config;
						}

					};
					rest.setParser(JackJsonUtils.getInstance());
				}
			}
		}
		return rest;
	}

	public static String getNoData() {
		return getInstance().getNoData();
	}

	public static String getErrorData(ErrorData errorData) {
		return getInstance().getErrorData(errorData);
	}

	public static String getObjectData(String objectName, Object object) {
		return getInstance().getObjectData(objectName, object);
	}

	public static String getListData(String listName, List<?> list) {
		return getInstance().getListData(listName, list);
	}

	public static String getPageData(String pageName, String pageDataName, Page page) {
		return getInstance().getPageData(pageName, pageDataName, page);
	}

	// 数据转换成json输出
	public static String getOriginData(Object obj) {
		return getInstance().getOriginData(obj);
	}

}
