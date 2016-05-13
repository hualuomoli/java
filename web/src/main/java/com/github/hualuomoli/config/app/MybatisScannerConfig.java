package com.github.hualuomoli.config.app;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
public class MybatisScannerConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisScannerConfig.class);

	@Bean
	public MapperScannerConfigurer loadMapperScannerConfigurer() {
		logger.info("instance mapperScannerConfigurer.");

		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.github.hualuomoli");
		mapperScannerConfigurer.setAnnotationClass(Repository.class);

		return mapperScannerConfigurer;
	}

}
