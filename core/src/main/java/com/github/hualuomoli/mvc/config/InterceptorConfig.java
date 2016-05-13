package com.github.hualuomoli.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.hualuomoli.mvc.interceptor.LogInterceptor;

/**
 * 拦截器
 * @author hualuomoli
 *
 */
@Configuration
@EnableWebMvc
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor());
	}

	@Bean
	public HandlerInterceptor logInterceptor() {
		return new LogInterceptor();
	}

}
