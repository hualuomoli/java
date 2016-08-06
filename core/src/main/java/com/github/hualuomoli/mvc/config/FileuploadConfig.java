package com.github.hualuomoli.mvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * 文件上传
 * @author hualuomoli
 *
 */
@Configuration
public class FileuploadConfig {

	private static final Logger logger = LoggerFactory.getLogger(FileuploadConfig.class);

	// 文件上传
	private long maxUploadSize = Long.parseLong(YamlUtils.getInstance().getString("upload", "maxSize"));
	private String defaultEncoding = YamlUtils.getInstance().getString("upload", "encoding");

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

}
