package com.github.hualuomoli.config;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Repository;

/**
 * 配置常量
 * @author hualuomoli
 *
 */
public class Config {

	// 扫描包,多个包名使用逗号(,)分割
	public static final String BASE_PACKAGE = "com.github.hualuomoli";
	// mybatis使用的注解类
	public static final Class<? extends Annotation> mapperAnnotationClass = Repository.class;

}
