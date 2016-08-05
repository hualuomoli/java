package com.github.hualuomoli.mvc.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.exception.AuthException;
import com.github.hualuomoli.exception.CodeException;
import com.github.hualuomoli.exception.CodeException.CodeError;
import com.github.hualuomoli.exception.InvalidEntityException;
import com.google.common.collect.Maps;

/**
 * 异常处理配置
 * @see #HandlerExceptionResolver
 * @author hualuomoli
 *
 */
@Configuration
@ControllerAdvice
public class ExceptionConfig {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

	private static final Integer INVALID_ENTITY_EXCEPTION = 9999; // 参数不合法

	// 业务错误
	@ExceptionHandler(value = CodeException.class)
	@ResponseStatus(HttpStatus.OK)
	public void invalidEntity(HttpServletResponse response, CodeException e) {
		this.flushJson(response, e.getCodeError());
	}

	// 实体类不合法
	@ExceptionHandler(value = InvalidEntityException.class)
	@ResponseStatus(HttpStatus.OK)
	public void invalidEntity(HttpServletResponse response, InvalidEntityException e) {
		this.flushJson(response, INVALID_ENTITY_EXCEPTION, StringUtils.join(e.getErrors(), ","));
	}

	// 没有权限
	@ExceptionHandler(value = AuthException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void unAuthorized(HttpServletResponse response, AuthException e) {
		this.flushJson(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}

	// 请求方法不允许
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public void methodNotAllowed(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
		this.flushJson(response, HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
	}

	// 请求协议不允许
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public void unsupportedMediaType(HttpServletResponse response, HttpMediaTypeNotSupportedException e) {
		this.flushJson(response, HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
	}

	// 运行时异常
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(HttpStatus.OK)
	public void runtimeException(HttpServletResponse response, RuntimeException e) {

		if (logger.isDebugEnabled()) {
			this.flushHtml(response, e);
		} else if (logger.isWarnEnabled()) {
			logger.warn("{}", e);
			this.flushJson(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} else {
			logger.error("{}", e);
			this.flushJson(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统错误");
		}

	}

	// 异常
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void exception(HttpServletResponse response, Exception e) {
		this.flushHtml(response, e);
	}

	// 输出html
	private void flushHtml(HttpServletResponse response, Exception e) {

		logger.error("{}", e);

		// 设置响应头
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		try {
			e.printStackTrace(response.getWriter());
		} catch (IOException e1) {
		}

	}

	// 输出JSON
	private void flushJson(HttpServletResponse response, CodeError codeError) {

		// 设置响应头
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");

		// 设置数据
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", codeError.getCode());
		map.put("msg", codeError.getMessage());
		String data = JsonUtils.getInstance().toJson(map);
		// 输出
		try {
			response.getOutputStream().write(data.getBytes());
		} catch (IOException e1) {
		}
	}

	// 输出JSON
	private void flushJson(HttpServletResponse response, Integer code, String message) {

		// 设置响应头
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");

		// 设置数据
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", code);
		map.put("msg", message);
		String data = JsonUtils.getInstance().toJson(map);
		// 输出
		try {
			response.getOutputStream().write(data.getBytes());
		} catch (IOException e1) {
		}
	}

}
