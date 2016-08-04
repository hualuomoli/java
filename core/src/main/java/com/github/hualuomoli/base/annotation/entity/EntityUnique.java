package com.github.hualuomoli.base.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 唯一性
 * @author hualuomoli
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityUnique {

	// 是否是联合索引
	boolean union() default false;

	// 属性列的名称
	String[] columnNmaes();

}
