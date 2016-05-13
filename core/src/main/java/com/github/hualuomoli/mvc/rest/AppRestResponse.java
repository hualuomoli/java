package com.github.hualuomoli.mvc.rest;

import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.mvc.rest.RestResponse.Config;
import com.github.hualuomoli.mvc.rest.RestResponse.ErrorData;

/**
 * APP的REST风格返回
 * @author hualuomoli
 * @see RestResponse
 *
 */
public abstract class AppRestResponse {

	private static Config config = new RestResponse.Config("0", "code", "msg", "data", "datas", "page", "total", "pageNumber", "pageSize", "dataList");

	private static RestResponse rest = new RestResponse() {

		@Override
		public Config getConfig() {
			return config;
		}

	};

	public static String getNoData() {
		return rest.getNoData();
	}

	public static String getErrorData(ErrorData errorData) {
		return rest.getErrorData(errorData);
	}

	public static String getObjectData(String objectName, Object object) {
		return rest.getObjectData(objectName, object);
	}

	public static String getListData(String listName, List<?> list) {
		return rest.getListData(listName, list);
	}

	public static String getPageData(String pageDataName, Page page) {
		return rest.getPageData(pageDataName, page);
	}

}
