package com.github.hualuomoli.plugin.mq;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
public class BaseConfig {

}
