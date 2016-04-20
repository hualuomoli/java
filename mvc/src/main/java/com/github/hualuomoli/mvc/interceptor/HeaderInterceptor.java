package com.github.hualuomoli.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.hualuomoli.mvc.parse.Parser;

public class HeaderInterceptor extends HandlerInterceptorAdapter {

	private Parser jsonParser;

	public void setJsonParser(Parser jsonParser) {
		this.jsonParser = jsonParser;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	void parseJsonParameter(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// parse json parameter
		if (StringUtils.startsWithIgnoreCase(request.getContentType(), "application/json")) {
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;

				MethodParameter[] parameters = handlerMethod.getMethodParameters();

				for (MethodParameter methodParameter : parameters) {
					// System.out.println(methodParameter.getParameterType());
					Class<?> parameterType = methodParameter.getParameterType();
					if (StringUtils.equals(parameterType.getName(), "javax.servlet.http.HttpServletRequest")) {
						continue;
					}
					if (StringUtils.equals(parameterType.getName(), "javax.servlet.http.HttpServletResponse")) {
						continue;
					}

					// TODO

					// jsonParser.parse(handler, "");
				}
			}
		}
	}

}
