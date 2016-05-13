package com.github.hualuomoli.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用Annotation自动注册Bean
 * @author hualuomoli
 *
 */
@Configuration
// 主容器中不扫描@Controller注解，在SpringMvc中只扫描@Controller注解(解决事物失效问题)
@ComponentScan( //
		basePackages = { "com.github.hualuomoli" }, //
		excludeFilters = { //
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }), //
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { RestController.class }) //
		} //
)
// 依赖除了mvc的功能
@Import({ DataSourceConfig.class, MybatisConfig.class, MybatisScannerConfig.class, TransactionConfig.class, BaseAspectConfig.class })
public class BaseConfig {

}
