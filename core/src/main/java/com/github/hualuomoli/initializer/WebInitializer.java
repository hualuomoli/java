package com.github.hualuomoli.initializer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 初始化web.xml
 * @author hualuomoli
 *
 */
@Order(2)
public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// filter
		_addServletFilters(servletContext);
	}

	private void _addServletFilters(ServletContext servletContext) {

		// 编码
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		servletContext.addFilter("encodingFilter", encodingFilter).addMappingForUrlPatterns(getDispatcherTypes(), isAsyncSupported(), "/");

	}

	private EnumSet<DispatcherType> getDispatcherTypes() {
		return isAsyncSupported() ? EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC)
				: EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
	}

	protected boolean isAsyncSupported() {
		return true;
	}
}
