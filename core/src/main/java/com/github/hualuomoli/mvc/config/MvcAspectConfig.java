package com.github.hualuomoli.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切面
 * @author hualuomoli
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MvcAspectConfig {

}
