package com.github.hualuomoli.rest;

import java.util.List;

import com.github.hualuomoli.base.entity.Page;

/**
 * APP的REST风格返回
 * @author hualuomoli
 *
 */
public class AppRestResponse extends RestResponse {

	public static final String CODE_NAME = "code";
	public static final String MSG_NAME = "msg";
	public static final String CODE_SUCCESS_VALUE = "0";
	public static final String OBJ_NAME = "data";
	public static final String LIST_NAME = "datas";

	public static final String PAGE_NAME = "page";
	public static final String PAGE_TOTAL_NAME = "total";
	public static final String PAGE_NUMBER_NAME = "pageNumber";
	public static final String PAGE_SIZE_NAME = "pageSize";
	public static final String PAGE_DATA_NAME = "dataList";

	// 不返回业务数据,只返回处理标识
	public static final String getNoData() {
		return getNoData(CODE_NAME, CODE_SUCCESS_VALUE);
	}

	// 不返回业务数据,只返回处理标识
	public static final String getErrorData(String codeErrorValue, String errorMessage) {
		return getErrorData(CODE_NAME, codeErrorValue, MSG_NAME, errorMessage);
	}

	// 返回数据对应JSON字符串
	public static final String getObjectData(Object obj) {
		return getObjectData(OBJ_NAME, obj);
	}

	// 返回数据对应JSON字符串
	public static final String getObjectData(String objName, Object obj) {
		return getObjectData(CODE_NAME, CODE_SUCCESS_VALUE, objName, obj);
	}

	// 返回集合数据对应JSON字符串
	public static final String getListData(List<?> list) {
		return getListData(LIST_NAME, list);
	}

	// 返回集合数据对应JSON字符串
	public static final String getListData(String listName, List<?> list) {
		return getListData(CODE_NAME, CODE_SUCCESS_VALUE, listName, list);
	}

	// 返回分页数据对应JSON字符串
	public static final String getPageData(Page page) {
		return getPageData(PAGE_NAME, PAGE_DATA_NAME, page);
	}

	// 返回分页数据对应JSON字符串
	public static final String getPageData(String pageName, String pageDataName, Page page) {
		return getPageData(CODE_NAME, CODE_SUCCESS_VALUE, pageName, PAGE_TOTAL_NAME, PAGE_NUMBER_NAME, PAGE_SIZE_NAME, pageDataName, page);
	}

}
