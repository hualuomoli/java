package com.github.hualuomoli.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
// XML中的<mvc:annotation-driven>功能一样；
// @EnableWebMvc
// 扫描controller注解
@ComponentScan(basePackages = "com.github.hualuomoli", useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
// 切面自动代理
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({ ViewConfig.class, FileuploadConfig.class })
public class MvcConfig {
}
