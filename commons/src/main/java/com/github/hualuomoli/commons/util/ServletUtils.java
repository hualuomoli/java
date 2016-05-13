package com.github.hualuomoli.commons.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * servlet 工具
 * @author hualuomoli
 *
 */
public class ServletUtils {

	/**
	 * 获取请求
	 * @return 请求
	 */
	public static final HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取相应
	 * @return 相应
	 */
	public static final HttpServletResponse getResponse() {
		try {
			return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
