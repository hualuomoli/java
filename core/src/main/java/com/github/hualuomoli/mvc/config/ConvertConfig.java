package com.github.hualuomoli.mvc.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.hualuomoli.commons.constant.Charset;
import com.github.hualuomoli.commons.jackjson.JackJsonMapper;

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

}
