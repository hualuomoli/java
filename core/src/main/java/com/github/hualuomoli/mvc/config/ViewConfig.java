package com.github.hualuomoli.mvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.hualuomoli.mvc.YamlMvcConfig;

@Configuration
public class ViewConfig {

	private static final Logger logger = LoggerFactory.getLogger(ViewConfig.class);

	// 视图
	private String prefix = YamlMvcConfig.getInstance().getValue("view.prefix");
	private String suffix = YamlMvcConfig.getInstance().getValue("view.suffix");

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

}
