package com.github.hualuomoli.config.app;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.hualuomoli.config.Config;

@Configuration
public class MybatisScannerConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisScannerConfig.class);

	@Bean
	public MapperScannerConfigurer loadMapperScannerConfigurer() {
		logger.info("instance mapperScannerConfigurer.");

		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(Config.BASE_PACKAGE);
		mapperScannerConfigurer.setAnnotationClass(Config.mapperAnnotationClass);

		return mapperScannerConfigurer;
	}

}
