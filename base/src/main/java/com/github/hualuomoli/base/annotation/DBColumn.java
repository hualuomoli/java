package com.github.hualuomoli.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库列
 * @author hualuomoli
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {

	// 是否非空
	boolean value() default false;

	// 数据类型
	String type() default "";

	// 字符串长度
	int length() default 0;

	// 精度(该值的总共长度)
	int precision() default 0;

	// 标度(小数点后面的长度)
	int scale() default 0;

	// 默认值
	String defaultValue() default "";

}
