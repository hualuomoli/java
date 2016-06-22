package com.github.hualuomoli.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.hualuomoli.config.app.AppConfig;
import com.github.hualuomoli.config.web.MvcConfig;

/**
 * 初始化
 * @author hualuomoli
 *
 */
@Order(Initializer.ORDER_COMMON)
public class CommonInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = _getRootContext();

		// 设置Spring监听器
		servletContext.addListener(new ContextLoaderListener(rootContext));
		// RequestContextListener

		// 设置转发servlet
		// rootContext可以不使用上面定义的,可以重新获取一个
		// rootContext = _getRootContext();
		servletContext.addServlet(Initializer.DEFAULT_SERVLET_NAME, new DispatcherServlet(rootContext)).addMapping("/");

	}

	// 获取Spring实例
	private AnnotationConfigWebApplicationContext _getRootContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(MvcConfig.class, AppConfig.class);
		return rootContext;
	}

}
