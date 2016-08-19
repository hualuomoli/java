package com.github.hualuomoli.extend.rest;

import java.util.List;
import java.util.Map;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.google.common.collect.Maps;

/**
 * RESTful风格响应
 * 1 返回成功编码
 * 2 返回错误编码和错误信息
 * 3 返回成功编码和数据Object
 * 4 返回成功编码和数据集合List
 * 5 返回数据编码和分页数据Page
 * 
 * 如果希望返回的格式统一,如object的key=data,list的key=datas,分页的数据key=dataList
 * 在请求头增加 force参数 req.header("force", "t")
 * 
 * @author hualuomoli
 *
 */
public abstract class RestResponse {

	// 获取配置信息
	public abstract Config getConfig();

	/**
	 * {
	 *   "code": "0"
	 * }
	 */
	// getNoData();
	// 不返回业务数据,只返回处理标识
	String getNoData() {
		Map<String, Object> map = Maps.newHashMap();
		map.put(getConfig().codeName, getConfig().successs);
		return this.toJson(map);
	}

	/**
	 * {
	 *   "code": "9999",
	 *   "msg": "用户名或密码错误"
	 * }
	 */
	// getNoData(error);
	// 不返回业务数据,只返回处理标识
	String getErrorData(ErrorData errorData) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(getConfig().codeName, errorData.code);
		map.put(getConfig().msgName, errorData.msg);
		return this.toJson(map);
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
	// getObjectData("user", user);
	// 返回数据对应JSON字符串
	public String getObjectData(String objectName, Object object) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(getConfig().codeName, getConfig().successs);
		map.put(objectName, object);
		return this.toJson(map);
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
	// getListData("citys", list);
	// 返回集合数据对应JSON字符串
	public String getListData(String listName, List<?> list) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(getConfig().codeName, getConfig().successs);
		map.put(listName, list);
		return this.toJson(map);
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
	// getPageData("page", "addresses", page)
	// 返回分页数据对应JSON字符串
	public String getPageData(String pageName, String pageDataName, Page page) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(getConfig().codeName, getConfig().successs);

		Map<String, Object> pageMap = Maps.newHashMap();
		pageMap.put(getConfig().pageTotalName, page.getCount());
		pageMap.put(getConfig().pageNumberName, page.getPageNo());
		pageMap.put(getConfig().pageSizeName, page.getPageSize());
		pageMap.put(pageDataName, page.getDataList());

		map.put(pageName, pageMap);

		return this.toJson(map);
	}

	// 转换成json输出
	public String toJson(Object obj) {
		return JsonUtils.toJson(obj);
	}

	// 配置信息
	static class Config {
		private Integer successs;
		private String codeName;
		private String msgName;
		private String pageTotalName;
		private String pageNumberName;
		private String pageSizeName;

		public Config(Integer successs, String codeName, String msgName, String pageTotalName, String pageNumberName, String pageSizeName) {
			this.successs = successs;
			this.codeName = codeName;
			this.msgName = msgName;
			this.pageTotalName = pageTotalName;
			this.pageNumberName = pageNumberName;
			this.pageSizeName = pageSizeName;
		}

	}

	// 错误信息
	public static class ErrorData {
		private Integer code;
		private String msg;

		public ErrorData(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}

	}

}
