package com.github.hualuomoli.base.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列信息
 * @author hualuomoli
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityColumn {

	// 数据库列名,默认使用驼峰命名法转换
	String name() default "";

	// 关联属性,默认为id关联
	String relation() default "id";

	// 数据类型
	EntityColumnType type() default EntityColumnType.AUTO;

	// 注释
	String comment() default "";

	// 是否允许为空
	boolean nullable() default true;

	// 字符串长度
	int length() default 0;

	// 精度(该值的总共长度)
	int precision() default 0;

	// 标度(小数点后面的长度)
	int scale() default 0;

	// 默认值
	String defaultValue() default "";

}
