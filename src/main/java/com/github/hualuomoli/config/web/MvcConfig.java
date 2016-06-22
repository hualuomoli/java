package com.github.hualuomoli.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;

import com.github.hualuomoli.config.Config;

@Configuration
// XML中的<mvc:annotation-driven>功能一样；
// @EnableWebMvc
// 扫描controller注解
@ComponentScan(basePackages = Config.BASE_PACKAGE, useDefaultFilters = false, includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
@PropertySource(value = { //
		"classpath:prop/mvc.properties", //
		"file:path/to/test", // 测试环境绝对路径
		"file:path/to/publish", // 生产环境绝对路径
}, ignoreResourceNotFound = true)
// 切面自动代理
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ ViewConfig.class, FileuploadConfig.class })
public class MvcConfig {
}
