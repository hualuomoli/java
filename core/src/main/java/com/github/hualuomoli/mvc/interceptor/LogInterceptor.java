package com.github.hualuomoli.mvc.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 日志拦截器
 * @author hualuomoli
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

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
			logger.debug("\t\t{} = {}", name, req.getParameter(name));
		}
	}

}
