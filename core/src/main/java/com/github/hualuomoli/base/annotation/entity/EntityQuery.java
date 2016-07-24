package com.github.hualuomoli.base.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询
 * @author hualuomoli
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityQuery {

	// 大于
	boolean greaterThan() default false;

	// 大于等于
	boolean greaterEqual() default false;

	// 小于
	boolean lessThan() default false;

	// 小于等于
	boolean lessEqual() default false;

	// 左like
	boolean leftLike() default false;

	// 右like
	boolean rightLike() default false;

	// 两边like
	boolean bothLike() default false;

	// 数组
	boolean inArray() default false;

}
