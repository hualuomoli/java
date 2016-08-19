package com.github.hualuomoli.mvc.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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

	// 日志拦截器
	private static class LogInterceptor extends HandlerInterceptorAdapter {

		private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			if (logger.isDebugEnabled()) {
				this.showRequestInformation(request);
			}
			return true;
		}

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		}

		/** 输出请求信息 */
		private void showRequestInformation(HttpServletRequest req) {
			// show request
			logger.debug("request message");
			logger.debug("\tcharacterEncoding {}", req.getCharacterEncoding());
			logger.debug("\tcontextPath {}", req.getContextPath());
			logger.debug("\tservletPath {}", req.getServletPath());
			logger.debug("\trequestedSessionId {}", req.getRequestedSessionId());
			logger.debug("\turl {}", req.getRequestURL());
			logger.debug("\turi {}", req.getRequestURI());
			logger.debug("\tmethod {}", req.getMethod());
			logger.debug("\theaders");
			Enumeration<String> headerNames = req.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				logger.debug("\t\t{} = {}", name, req.getHeader(name));
			}
			logger.debug("\tparameters");
			Enumeration<String> parameterNames = req.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				String[] values = req.getParameterValues(name);
				logger.debug("\t\t{} = {}", name, values);
			}
		}

	}

}
