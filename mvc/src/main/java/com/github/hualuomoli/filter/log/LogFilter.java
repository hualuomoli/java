package com.github.hualuomoli.filter.log;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.filter.FilterBean;

public class LogFilter extends FilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// log request
		this.log((HttpServletRequest) request);
		// chain
		chain.doFilter(request, response);
		// log response
		this.log((HttpServletResponse) response);
	}

	private void log(HttpServletRequest req) {
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

	private void log(HttpServletResponse res) {

	}

}
