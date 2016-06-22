package com.github.hualuomoli.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列信息
 * @author admin
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	// 数据库表名,默认使用驼峰命名法转换
	String name() default "";

	// 注释
	String comment() default "";

}
