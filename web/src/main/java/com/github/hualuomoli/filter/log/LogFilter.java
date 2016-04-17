package com.github.hualuomoli.filter.log;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (allowLog && logger.isDebugEnabled()) {
			// show request
			logger.debug("request message");
			logger.debug("\tcharacterEncoding {}", request.getCharacterEncoding());
			logger.debug("\tcontextPath {}", request.getContextPath());
			logger.debug("\tservletPath {}", request.getServletPath());
			logger.debug("\trequestedSessionId {}", request.getRequestedSessionId());
			logger.debug("\turl {}", request.getRequestURL());
			logger.debug("\turi {}", request.getRequestURI());
			logger.debug("\tmethod {}", request.getMethod());
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				logger.debug("\tcookies");
				for (Cookie cookie : cookies) {
					logger.debug("\t\t{} = {}", cookie.getName(), cookie.getValue());
				}
			}
			logger.debug("\theaders");
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				logger.debug("\t\t{} = {}", name, request.getHeader(name));
			}
			logger.debug("\tparameters");
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				logger.debug("\t\t{} = {}", name, request.getParameter(name));
			}
		}
		return true;
	}

}
