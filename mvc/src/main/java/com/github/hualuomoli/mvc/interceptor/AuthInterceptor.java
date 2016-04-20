package com.github.hualuomoli.mvc.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.hualuomoli.mvc.security.Auth;
import com.github.hualuomoli.mvc.security.entity.User;
import com.github.hualuomoli.mvc.security.exception.AuthException;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private Auth auth;

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = getUser(request);
		if (StringUtils.isEmpty(user.getToken())) {
			throw new AuthException(Auth.ERROR_CODE_NO_LOGIN, "未登录,请先登录");
		}
		if (!auth.isLogin(user)) {
			throw new AuthException(Auth.ERROR_CODE_OVERTIME, "登录超时,请重新登录");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		refreshToken(request, response);
	}

	// 获取token
	public static String getToken(HttpServletRequest request) {
		String token = null;
		// get token from cookie
		token = getCookieValue(request, User.TOKEN);
		if (StringUtils.isNotBlank(token)) {
			return token;
		}

		return request.getHeader(User.TOKEN);
	}

	// 设置token
	public static void setToken(HttpServletResponse response, String token) {
		// add to cookie
		response.addCookie(new Cookie(User.TOKEN, token));
		// add to header
		response.setHeader(User.TOKEN, token);
	}

	// 刷新token
	public static void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		setToken(response, getToken(request));
	}

	// get cookie value
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie == null) {
			return null;
		}
		return cookie.getValue();
	}

	// get cookie
	public static Cookie getCookie(HttpServletRequest request, String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (StringUtils.equals(cookie.getName(), name)) {
				return cookie;
			}
		}
		return null;
	}

	// 获取username
	public static String getUsername(HttpServletRequest request) {
		String username = null;
		// get from cookie
		username = getCookieValue(request, User.USERNAME);
		if (StringUtils.isNotBlank(username)) {
			return username;
		}
		// get from header
		username = request.getHeader(User.USERNAME);
		if (StringUtils.isNotEmpty(username)) {
			return username;
		}
		// get from parameter
		return request.getParameter(User.USERNAME);
	}

	// 获取user
	public static User getUser(HttpServletRequest request) {
		User user = new User();
		user.setUsername(getUsername(request));
		user.setToken(getToken(request));
		return user;
	}

}
