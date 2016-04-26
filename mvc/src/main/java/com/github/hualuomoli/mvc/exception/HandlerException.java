package com.github.hualuomoli.mvc.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.github.hualuomoli.commons.util.CharsetUtils;
import com.github.hualuomoli.mvc.exception.entity.ErrorData;
import com.github.hualuomoli.mvc.security.exception.AuthException;
import com.github.hualuomoli.mvc.security.exception.InvalidParameterException;
import com.github.hualuomoli.mvc.security.exception.MvcException;

@Service(value = "com.github.hualuomoli.mvc.exception.HandlerException")
public class HandlerException implements HandlerExceptionResolver {

	public static final String JSON = "application/json;charset=utf-8";

	public static final int STATUS_SUCCESS = 200; // 成功
	public static final int STATUS_NO_AUTH = 401; // 没有权限
	public static final int STSTUS_INVLID_PARAMETER = 400; // 参数不合法
	public static final int STATUS_MVC_CUSTOM = 200; // 用户自定义异常
	public static final int STATUS_ERROR = 500; // 错误

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		// 设置编码集
		response.setCharacterEncoding(CharsetUtils.UTF8.name());
		// 设置返回数据类型
		response.setContentType(JSON);

		// 设置错误信息
		ErrorData errorData;

		// MVC 异常,设置异常信息
		if (ex instanceof MvcException) {
			// MVC自定义异常
			MvcException mvcException = (MvcException) ex;
			errorData = mvcException.getErrorData();

			// 设置status
			if (ex instanceof AuthException) {
				// 权限异常 status = 401
				response.setStatus(STATUS_NO_AUTH);
			} else if (ex instanceof InvalidParameterException) {
				// 参数不合法 = 400
				response.setStatus(STSTUS_INVLID_PARAMETER);
			} else {
				// 自定义异常 status = 200
				response.setStatus(STATUS_MVC_CUSTOM);
			}
		} else {
			// 其他未知异常
			ex.printStackTrace();
			errorData = new ErrorData();
			errorData.setCode(String.valueOf(STATUS_ERROR));
			// errorData.setMsg(ex.getMessage());
			// 其他异常 status = 500
			response.setStatus(STATUS_ERROR);
		}

		try {
			response.getWriter().write(errorData.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView();
	}

}
