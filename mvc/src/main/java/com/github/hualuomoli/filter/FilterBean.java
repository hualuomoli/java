package com.github.hualuomoli.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.error.Result;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public abstract class FilterBean implements Filter {

	public static final int STATUS_SUCCESS = 200;
	public static final int STATUS_ERROR = 500;
	public static final int STATUS_NO_AUTHOR = 401;
	public static final int STATUS_FORBIDDEN = 403;

	public static final String CODE = "code";
	public static final String MSG = "msg";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// cross domain
	public static final int CAN_NOT_CROSS = 50010;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			this.config(filterConfig);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private void config(FilterConfig filterConfig)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<? extends FilterBean> cls = this.getClass();
		Set<Field> fields = this.getFields(this.getClass());

		for (Field field : fields) {
			String name = field.getName();
			// get name's value
			// if the value is not null
			// configure value
			String value = filterConfig.getInitParameter(name);
			if (StringUtils.isNotBlank(value)) {
				String setMethod = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
				Class<?> type = field.getType();
				if (StringUtils.equals(type.getName(), "java.lang.String")) {
					cls.getMethod(setMethod, String.class).invoke(this, value);
				} else if (StringUtils.equals(type.getName(), "int")) {
					cls.getMethod(setMethod, int.class).invoke(this, Integer.parseInt(value));
				} else if (StringUtils.equals(type.getName(), "java.lang.Integer")) {
					cls.getMethod(setMethod, Integer.class).invoke(this, Integer.valueOf(value));
				} else if (StringUtils.equals(type.getName(), "long")) {
					cls.getMethod(setMethod, long.class).invoke(this, Long.parseLong(value));
				} else if (StringUtils.equals(type.getName(), "java.lang.Long")) {
					cls.getMethod(setMethod, Long.class).invoke(this, Long.valueOf(value));
				} else if (StringUtils.equals(type.getName(), "boolean")) {
					cls.getMethod(setMethod, boolean.class).invoke(this, Boolean.parseBoolean(value));
				} else if (StringUtils.equals(type.getName(), "java.lang.Boolean")) {
					cls.getMethod(setMethod, Boolean.class).invoke(this, Boolean.valueOf(value));
				} else {
					String[] array = value.split("[,]");
					if (StringUtils.equals(type.getName(), "java.util.List")) {
						cls.getMethod(setMethod, List.class).invoke(this, Lists.newArrayList(array));
					} else if (StringUtils.equals(type.getName(), "java.util.Set")) {
						cls.getMethod(setMethod, Set.class).invoke(this, Sets.newHashSet(array));
					}
				}

			}
		}
	}

	private Set<Field> getFields(Class<?> cls) {
		Set<Field> fields = Sets.newHashSet();
		while (cls.getSuperclass() != null) {
			fields.addAll(Sets.newHashSet(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		return fields;
	}

	@Override
	public void destroy() {
	}

	// set success
	protected void setSuccess(HttpServletResponse res) {
		res.setStatus(STATUS_SUCCESS);
	}

	/** 错误 */
	protected void setError(HttpServletResponse res, Result result) {
		res.setStatus(STATUS_ERROR);
		this.addMessage(res, result);
	}

	/** 没有权限 401 */
	protected void setNoAuthor(HttpServletResponse res, Result result) {
		res.setStatus(STATUS_NO_AUTHOR);
		this.addMessage(res, result);
	}

	/** 禁止403 */
	protected void setForbidden(HttpServletResponse res, Result result) {
		res.setStatus(STATUS_FORBIDDEN);
		this.addMessage(res, result);
	}

	// 增加header
	private void addMessage(HttpServletResponse res, Result result) {
		// set content-type
		res.setContentType("application/json");
		// add to header
		res.addHeader(CODE, String.valueOf(result.getCode()));
		res.addHeader(MSG, result.getMessage());
		// add to return
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");

		// code
		buffer.append("\"").append(CODE).append("\"");
		buffer.append(":");
		buffer.append("\"").append(result.getCode()).append("\"");

		// ,
		buffer.append(",");

		// message
		buffer.append("\"").append(MSG).append("\"");
		buffer.append(":");
		buffer.append("\"").append(result.getMessage()).append("\"");

		buffer.append("}");

		try {
			PrintWriter writer = res.getWriter();
			writer.write(buffer.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
