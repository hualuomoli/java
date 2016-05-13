package com.github.hualuomoli.config.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * 使用Annotation自动注册Bean
 * @author hualuomoli
 *
 */
@Configuration
// 主容器中不扫描@Controller注解，在SpringMvc中只扫描@Controller注解(解决事物失效问题)
@ComponentScan(basePackages = "com.github.hualuomoli", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
// 切面自动代理
@EnableAspectJAutoProxy(proxyTargetClass = true)
// 依赖除了mvc的功能
@Import({ DataSourceConfig.class, MybatisConfig.class, MybatisScannerConfig.class })
public class AppConfig {

}
