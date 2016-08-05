package com.github.hualuomoli.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.github.hualuomoli.commons.jackjson.JackJsonMapper;
import com.github.hualuomoli.commons.parser.JSONParser;

/**
 * MVC的JSON工具
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
public class JackJsonUtils extends JackJsonMapper implements JSONParser {

	private static final JackJsonUtils parser = new JackJsonUtils();

	public static JackJsonUtils getInstance() {
		return parser;
	}

	// 转换成JSON
	public final String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	// JSON数据转换成Object
	public final <T> T parseObject(String jsonString, Class<T> cls) {
		try {
			return this.readValue(jsonString, cls);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	// JSON数据转换成List
	public final <T> List<T> parseList(String jsonString, Class<T> cls) {
		try {
			JavaType javaType = this.constructParametricType(List.class, cls);
			return this.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 创建集合的javaType
	 * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 * 
	 * @param collectionClass 集合的类
	 * @param elementClasses 属性的类
	 * @return javaType
	 */
	public JavaType constructParametricType(Class<?> collectionClass, Class<?>... elementClasses) {
		return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
