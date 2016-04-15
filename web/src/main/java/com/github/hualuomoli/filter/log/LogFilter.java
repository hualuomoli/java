package com.github.hualuomoli.filter.log;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.github.hualuomoli.filter.FilterBean;

public class LogFilter extends FilterBean {

	private boolean allowLog;

	public void setAllowLog(boolean allowLog) {
		this.allowLog = allowLog;
	}

	@Override
	protected void configure() {
		if (logger.isDebugEnabled()) {
			logger.debug("allowLog {}", allowLog);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (allowLog && logger.isDebugEnabled()) {
			// show request
			HttpServletRequest req = (HttpServletRequest) request;
			logger.debug("request message");
			logger.debug("\tcharacterEncoding {}", req.getCharacterEncoding());
			logger.debug("\tcontextPath {}", req.getContextPath());
			logger.debug("\tservletPath {}", req.getServletPath());
			logger.debug("\trequestedSessionId {}", req.getRequestedSessionId());
			logger.debug("\turl {}", req.getRequestURL());
			logger.debug("\turi {}", req.getRequestURI());
			logger.debug("\tmethod {}", req.getMethod());
			logger.debug("\tcookies");
			Cookie[] cookies = req.getCookies();
			for (Cookie cookie : cookies) {
				logger.debug("\t\t{} = {}", cookie.getName(), cookie.getValue());
			}
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
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
