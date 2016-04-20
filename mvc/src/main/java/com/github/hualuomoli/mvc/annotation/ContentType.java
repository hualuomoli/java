package com.github.hualuomoli.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

/**
 * 响应数据类型
 * @author hualuomoli
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ContentType {

	public static final String JSON = "application/json;charset=utf-8";
	public static final String XMl = "application/xml;charset=utf-8";
	public static final String TEXT = "text/plain;charset=utf-8";

	String value() default JSON;

}
