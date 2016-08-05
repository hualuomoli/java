package com.github.hualuomoli.commons.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.hualuomoli.commons.parser.JSONParser;

/**
 * JSON工具
 * @author hualuomoli
 *
 */
public class JsonUtils implements JSONParser {

	private static final JsonUtils parser = new JsonUtils();

	// 实例
	public static JsonUtils getInstance() {
		return parser;
	}

	// 转换成JSON
	public final String toJson(Object object) {
		return JSON.toJSONString(object);
	}

	// JSON数据转换成Object
	public final <T> T parseObject(String jsonString, Class<T> cls) {
		return JSON.parseObject(jsonString, cls);
	}

	// JSON数据转换成List
	public final <T> List<T> parseList(String jsonString, Class<T> cls) {
		return JSON.parseArray(jsonString, cls);
	}

}
