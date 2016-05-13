package com.github.hualuomoli.mvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
// 扫描controller注解
@ComponentScan(//
		basePackages = { "com.github.hualuomoli" }, //
		useDefaultFilters = false, //
		includeFilters = { //
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }), //
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { RestController.class }) //
		}//
)
@Import({ ConvertConfig.class, InterceptorConfig.class, MvcAspectConfig.class, ViewConfig.class, FileuploadConfig.class, ExceptionConfig.class })
public class MvcConfig {
}
