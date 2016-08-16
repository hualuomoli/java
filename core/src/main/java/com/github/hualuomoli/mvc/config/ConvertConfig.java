package com.github.hualuomoli.mvc.config;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
import com.github.hualuomoli.commons.constant.Charset;

/**
 * 消息转换
 * @author hualuomoli
 *
 */
@Configuration
@EnableWebMvc
public class ConvertConfig extends WebMvcConfigurerAdapter {

	// 配置消息转换器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringConvert());
		converters.add(jsonConvert());
	}

	// 字符串转换
	@Bean
	public StringHttpMessageConverter stringConvert() {
		return new StringHttpMessageConverter(Charset.UTF8);
	}

	// json转换
	@Bean
	public MappingJackson2HttpMessageConverter jsonConvert() {

		MappingJackson2HttpMessageConverter convert = new MappingJackson2HttpMessageConverter();
		convert.setObjectMapper(new JackJsonMapper());
		convert.setPrettyPrint(true);

		return convert;
	}

	// 简单处理ObjectMapper
	@SuppressWarnings("serial")
	public static class JackJsonMapper extends ObjectMapper {
		public JackJsonMapper() {
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

}
