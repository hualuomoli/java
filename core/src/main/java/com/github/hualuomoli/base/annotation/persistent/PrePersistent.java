package com.github.hualuomoli.base.annotation.persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 持久化预处理
 * @author hualuomoli
 *
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrePersistent {

	// 持久化类型
	Type type();

}
