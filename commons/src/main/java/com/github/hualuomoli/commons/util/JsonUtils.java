package com.github.hualuomoli.commons.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * JSON工具
 * @author hualuomoli
 *
 */
public class JsonUtils {

	// 转换成JSON
	public static final String toJson(Object object) {
		return JSON.toJSONString(object);
	}

	// JSON数据转换成Object
	public static final <T> T parseObject(String jsonString, Class<T> cls) {
		return JSON.parseObject(jsonString, cls);
	}

	// JSON数据转换成List
	public static final <T> List<T> parseList(String jsonString, Class<T> cls) {
		return JSON.parseArray(jsonString, cls);
	}

}
