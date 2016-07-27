package com.github.hualuomoli.commons.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * servlet 工具
 * @author hualuomoli
 *
 */
public class ServletUtils {

	private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	/**
	 * 获取请求
	 * @return 请求
	 */
	public static final HttpServletRequest getRequest() {

		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (servletRequestAttributes == null) {
				return null;
			}
			return servletRequestAttributes.getRequest();
		} catch (Exception e) {
			logger.warn("{}", e);
			return null;
		}
	}

	/**
	 * 获取相应
	 * @return 相应
	 */
	public static final HttpServletResponse getResponse() {

		try {
			HttpServletRequest request = getRequest();
			if (request == null) {
				return null;
			}
			ServletWebRequest servletWebRequest = new ServletWebRequest(request);
			return servletWebRequest.getResponse();
		} catch (Exception e) {
			logger.warn("{}", e);
			return null;
		}
	}

	// 获取Cookie值
	public static final String getCookie(String name) {
		HttpServletRequest req = getRequest();
		if (req == null) {
			return null;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (StringUtils.equals(cookie.getName(), name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	// 获取Header值
	public static final String getHeader(String name) {
		HttpServletRequest req = getRequest();
		if (req == null) {
			return null;
		}
		return req.getHeader(name);
	}

	// 获取请求
	public static final String getParameter(String name) {
		try {
			HttpServletRequest request = getRequest();
			if (request == null) {
				return null;
			}
			// 获取原始编码集
			String encoding = request.getCharacterEncoding();
			// 设置为UTF-8
			request.setCharacterEncoding("UTF-8");
			// 获取值
			String value = request.getParameter(name);
			// 设置回源编码集
			request.setCharacterEncoding(encoding);
			return value;
		} catch (Exception e) {
			logger.warn("{}", e);
			return null;
		}

	}

}
