package com.github.hualuomoli.base.config;

import java.lang.annotation.Annotation;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.base.YamlBaseConfig;

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
		logger.info("instance mapperScannerConfigurer.");

		String basePackage = YamlBaseConfig.getInstance().getValue("mybatis.basePackage");
		String annotationClassName = YamlBaseConfig.getInstance().getValue("mybatis.annotationClass");
		Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) Class.forName(annotationClassName);

		if (logger.isDebugEnabled()) {
			logger.debug("basePackage {}", basePackage);
			logger.debug("annotationClass {}", annotationClassName);
		}

		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(basePackage);
		mapperScannerConfigurer.setAnnotationClass(annotationClass);

		return mapperScannerConfigurer;
	}

}
