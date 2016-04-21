package com.github.hualuomoli.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.hualuomoli.commons.servlet.TokenUtils;
import com.github.hualuomoli.mvc.security.Auth;
import com.github.hualuomoli.mvc.security.exception.AuthException;
import com.github.hualuomoli.mvc.security.exception.MvcException;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private Auth auth;

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = TokenUtils.getToken(request);
		if (StringUtils.isEmpty(token)) {
			throw new AuthException(MvcException.ERROR_AUTH_NO_LOGIN, "未登录,请先登录");
		}
		if (!auth.isLogin(token)) {
			throw new AuthException(MvcException.ERROR_AUTH_OVERTIME, "登录超时,请重新登录");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		TokenUtils.refreshToken(request, response);
	}

}
