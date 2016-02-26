package com.github.hualuomoli.commons.json;

import java.io.IOException;
import java.util.TimeZone;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * JSON转换器
 * @author liubaoquan
 *
 */
public abstract class BaseJsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 7432508237783832458L;
	protected static final Logger logger = LoggerFactory.getLogger(BaseJsonMapper.class);

	protected BaseJsonMapper() {
	}

	protected BaseJsonMapper(JsonConfigure jsonConfigure) {
		jsonConfigure.config(this);
	}

	// 配置
	protected void config() {
		// 设置输出时包含属性的风格
		this.setSerializationInclusion(Include.ALWAYS);
		this.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		this.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 允许单引号、允许不带引号的字段名称
		this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// enum
		this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 空值处理为空串
		// this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
		// @Override
		// public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		// jgen.writeString("");
		// }
		// });
		// 进行HTML解码。
		this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>() {
			@Override
			public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
				if (value != null) {
					jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
				}
			}
		}));
		// 设置时区
		this.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	}

	/**
	 * 序列化 Object可以是POJO，也可以是Collection或数组。
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 * 
	 * @param object data
	 * @return 序列化后的数据
	 */
	protected String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 反序列化POJO
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * @param jsonString JSON data
	 * @param clazz POJO class
	 * @return POJO instance
	 */
	protected <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return this.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 反序列化复杂Collection如List<Bean>
	 * @param jsonString JSON data
	 * @param javaType java type {@link #getJavaType(Class, Class...)}
	 * @return 集合
	 */
	@SuppressWarnings("unchecked")
	protected <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return (T) this.readValue(jsonString, javaType);
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
