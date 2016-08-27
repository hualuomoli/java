package com.github.hualuomoli.base.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.type.BaseTypeHandler;

/**
 * 自定义类型
 * @author hualuomoli
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityColumnCustom {

	Class<? extends BaseTypeHandler<?>> typeHander();;

}
