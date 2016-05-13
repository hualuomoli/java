package com.github.hualuomoli.commons.jackjson;

import java.io.IOException;
import java.util.TimeZone;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jack JSON工具
 * @author hualuomoli
 *
 */
public class JackJsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 3846794644632299258L;

	protected static final Logger logger = LoggerFactory.getLogger(JackJsonMapper.class);

	public JackJsonMapper() {
		super();
		this.config();
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
		// public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
		// JsonProcessingException {
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

}
