package com.github.hualuomoli.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
// 扫描controller注解
@PropertySource(value = { //
		"classpath:prop/mvc.properties", //
		"file:path/to/test", // 测试环境绝对路径
		"file:path/to/publish", // 生产环境绝对路径
}, ignoreResourceNotFound = true)
public class FileuploadConfig {

	private static final Logger logger = LoggerFactory.getLogger(FileuploadConfig.class);

	// 文件上传
	@Value(value = "${mvc.upload.maxSize}")
	private long maxUploadSize;
	@Value(value = "${mvc.upload.encoding}")
	private String defaultEncoding;

	// 文件上传
	@Bean
	public MultipartResolver multipartResolver() {
		logger.info("register multipartResolver");
		if (logger.isDebugEnabled()) {
			logger.debug("maxUploadSize {}", maxUploadSize);
			logger.debug("defaultEncoding {}", defaultEncoding);
		}

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(maxUploadSize);
		multipartResolver.setDefaultEncoding(defaultEncoding);
		return multipartResolver;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
