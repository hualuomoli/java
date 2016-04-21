package com.github.hualuomoli.commons.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * servlet 工具
 * @author hualuomoli
 *
 */
public class ServletUtils {

	/** get custom real ip */
	public static String getCustomIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 从cookie获取值
	 * @param request req
	 * @param name cookie's name
	 * @return cookie's value if exists
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 从cookie获取
	 * @param request req
	 * @param name cookie's name
	 * @return cookie if exists
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (StringUtils.equalsIgnoreCase(cookie.getName(), name)) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 设置cookie
	 * @param response res
	 * @param name cookie's name
	 * @param value cookie's value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		response.addCookie(new Cookie(name, value));
	}

	/**
	 * 从header获取
	 * @param request req
	 * @param name header's name
	 * @return header's value
	 */
	public static String getHeader(HttpServletRequest request, String name) {
		return request.getHeader(name);
	}

	/**
	 * 设置header
	 * @param response res
	 * @param name header's name
	 * @param value header's value
	 */
	public static void setHeader(HttpServletResponse response, String name, String value) {
		response.setHeader(name, value);
	}

}
