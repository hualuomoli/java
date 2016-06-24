package com.github.hualuomoli.rest;

import java.util.List;
import java.util.Map;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.json.JsonMapper;
import com.google.common.collect.Maps;

/**
 * RESTful风格响应
 * @author hualuomoli
 *
 */
public class RestResponse {

	/**
	 * {
	 *   "code": "0"
	 * }
	 */
	// getNoData("code", "0");
	// 不返回业务数据,只返回处理标识
	public static final String getNoData(String codeName, String codeSuccessValue) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(codeName, codeSuccessValue);
		return JsonMapper.toJsonString(map);
	}

	/**
	 * {
	 *   "code": "9999",
	 *   "用户名或密码错误"
	 * }
	 */
	// getNoData("code", "9999", "msg", "用户名或密码错误");
	// 不返回业务数据,只返回处理标识
	public static final String getErrorData(String codeName, String codeErrorValue, String messageName, Object errorMessage) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(codeName, codeErrorValue);
		map.put(messageName, errorMessage);
		return JsonMapper.toJsonString(map);
	}

	/**
	 * {
	 *   "code": "0",
	 *   "user": {
	 *   	"username": "hualuomoli",
	 *   	"nickname": "花落莫离",
	 *   	"age": 20
	 *   }
	 * }
	 */
	// getObjectData("code", "0", "user", user);
	// 返回数据对应JSON字符串
	public static final String getObjectData(String codeName, String codeSuccessValue, String objName, Object obj) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(codeName, codeSuccessValue);
		map.put(objName, obj);
		return JsonMapper.toJsonString(map);
	}

	/**
	 * {
	 *   "code": "0",
	 *   "citys": [{
	 *   	"code": "37",
	 *   	"name": "山东",
	 *   	"sort": 1
	 *   }, {
	 *   	"code": "01",
	 *   	"name": "北京",
	 *   	"sort": 2
	 *   }]
	 * }
	 */
	// getListData("code", "0", "citys", list);
	// 返回集合数据对应JSON字符串
	public static final String getListData(String codeName, String codeSuccessValue, String listName, List<?> list) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(codeName, codeSuccessValue);
		map.put(listName, list);
		return JsonMapper.toJsonString(map);
	}

	/**
	 * {
	 *   "code": "0",
	 *   "page": {
	 *   	"total": 100,
	 *   	"pageNumber": 5,
	 *   	"pageSize": 10,
	 *   	"addresses": [{
	 *   		"province": "37",
	 *   		"city": "3702",
	 *   		"couty": "370203",
	 *   		"description": "合肥路666号"
	 *   	}, {
	 *   		"province": "37",
	 *   		"city": "3702",
	 *   		"couty": "370202",
	 *   		"description": "台东步行街"
	 *   	}]
	 *   }
	 * }
	 */
	// getPageData("code", "0", "page", "total", "pageNumber", "pageSize", "addresses", page)
	// 返回分页数据对应JSON字符串
	public static final String getPageData(String codeName, String codeSuccessValue, String pageName, String pageTotalName, String pageNumberName, String pageSizeName, String pageDataName, Page page) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(codeName, codeSuccessValue);

		Map<String, Object> pageMap = Maps.newHashMap();
		pageMap.put(pageTotalName, page.getCount());
		pageMap.put(pageNumberName, page.getPageNo());
		pageMap.put(pageSizeName, page.getPageSize());
		pageMap.put(pageDataName, page.getDataList());

		map.put(pageName, pageMap);

		return JsonMapper.toJsonString(map);
	}

}
