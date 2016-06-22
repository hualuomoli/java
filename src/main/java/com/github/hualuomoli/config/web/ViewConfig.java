package com.github.hualuomoli.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
// 扫描controller注解
@PropertySource(value = { //
		"classpath:prop/mvc.properties", //
		"file:path/to/test", // 测试环境绝对路径
		"file:path/to/publish", // 生产环境绝对路径
}, ignoreResourceNotFound = true)
public class ViewConfig {

	private static final Logger logger = LoggerFactory.getLogger(ViewConfig.class);

	// 视图
	@Value(value = "${mvc.view.prefix}")
	private String prefix;
	@Value(value = "${mvc.view.suffix}")
	private String suffix;

	// 视图
	@Bean
	public ViewResolver viewResolver() {
		logger.info("register ViewResolver");
		if (logger.isDebugEnabled()) {
			logger.debug("prefix {}", prefix);
			logger.debug("suffix {}", suffix);
		}

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix(prefix);
		viewResolver.setSuffix(suffix);
		return viewResolver;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
