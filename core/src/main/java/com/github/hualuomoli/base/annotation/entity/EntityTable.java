package com.github.hualuomoli.base.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表信息
 * @author hualuomoli
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityTable {

	// 数据库表名,默认使用驼峰命名法转换
	String name() default "";

	// 注释
	String comment();

	// 高并发
	boolean higher() default false;

	// 忽略属性
	String[] ignores() default {};

	// 唯一属性
	String[] unique() default {};

}
