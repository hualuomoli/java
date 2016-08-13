package com.github.hualuomoli.base.config;

import java.lang.annotation.Annotation;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.commons.util.YamlUtils;

/**
 * Mybatis扫描配置,需要与mybatis的其他配置分开
 * @author hualuomoli
 *
 */
@Configuration
public class MybatisScannerConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisScannerConfig.class);

	@SuppressWarnings("unchecked")
	@Bean
	public MapperScannerConfigurer loadMapperScannerConfigurer() throws ClassNotFoundException {
		if (logger.isInfoEnabled()) {
			logger.info("instance mapperScannerConfigurer.");
		}

		String basePackage = YamlUtils.getInstance().getString("mybatis", "basePackage");
		String annotationClassName = YamlUtils.getInstance().getString("mybatis", "annotationClass");
		Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) Class.forName(annotationClassName);

		if (logger.isInfoEnabled()) {
			logger.info("basePackage {}", basePackage);
			logger.info("annotationClass {}", annotationClassName);
		}

		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(basePackage);
		mapperScannerConfigurer.setAnnotationClass(annotationClass);

		return mapperScannerConfigurer;
	}

}
