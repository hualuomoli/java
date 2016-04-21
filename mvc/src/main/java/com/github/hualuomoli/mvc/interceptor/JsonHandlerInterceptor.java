package com.github.hualuomoli.mvc.interceptor;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.hualuomoli.mvc.parse.Parser;
import com.google.common.collect.Lists;

public class JsonHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final List<MediaType> contentTypes = Lists.newArrayList(new MediaType("application", "json", DEFAULT_CHARSET));

	private String entityPackage;

	private Parser parser;

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	// 是否支持
	private boolean support(MediaType mediaType) {
		if (mediaType == null) {
			return false;
		}
		for (MediaType supportMediaType : contentTypes) {
			if (supportMediaType.isCompatibleWith(mediaType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String contentType = request.getContentType();
//		System.out.println(contentType);
//		if (StringUtils.isNotBlank(contentType)) {
//
//			MediaType mediaType = MediaType.parseMediaType(contentType);
//			if (this.support(mediaType)) {
//				this.read(request, response, mediaType, handler);
//			}
//		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	private void read(HttpServletRequest request, HttpServletResponse response, MediaType mediaType, Object handler) {
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			System.out.println(method);
			MethodParameter[] parameters = ((HandlerMethod) handler).getMethodParameters();
			MethodParameter parameter = this.getEntity(parameters);
			System.out.println(parameter);
		}
		System.out.println(handler.getClass().getName());
	}

	private MethodParameter getEntity(MethodParameter[] parameters) {
		MethodParameter parameter = null;
		for (MethodParameter methodParameter : parameters) {
			if (methodParameter.getParameterType().getName().startsWith(entityPackage)) {
				return methodParameter;
			}
		}
		return parameter;
	}

}
