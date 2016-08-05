package com.github.hualuomoli.commons.parser;

import java.util.List;

/**
 * JSON转换器
 * @author hualuomoli
 *
 */
public interface JSONParser {

	// 转换成JSON
	String toJson(Object object);

	// JSON数据转换成Object
	<T> T parseObject(String jsonString, Class<T> cls);

	// JSON数据转换成List
	<T> List<T> parseList(String jsonString, Class<T> cls);

}
