package com.github.hualuomoli.commons.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 工具
 * @author hualuomoli
 *
 */
public class TokenUtils {

	public static final String TOKEN = "token";

	public static String getToken(HttpServletRequest request) {
		String token;

		// get from cookie
		token = ServletUtils.getCookieValue(request, TOKEN);
		if (token != null) {
			return token;
		}
		// get from header
		token = ServletUtils.getHeader(request, TOKEN);
		if (token != null) {
			return token;
		}

		//
		return token;
	}

	// set token
	public static void setToken(HttpServletResponse response, String token) {
		ServletUtils.setCookie(response, TOKEN, token);
		ServletUtils.setHeader(response, TOKEN, token);
	}

	// 刷新token
	public static void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		setToken(response, getToken(request));
	}

}
