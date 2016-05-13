package com.github.hualuomoli.base.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.github.hualuomoli.base.config.BaseConfig;

/**
 * 测试
 * @author admin
 *
 */
public class AnnotationConfigTest {

	protected static final Logger logger = LoggerFactory.getLogger(AnnotationConfigTest.class);
	protected static AnnotationConfigWebApplicationContext rootContext = null;

	static {
		rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(BaseConfig.class);
		rootContext.refresh();
	}
}
