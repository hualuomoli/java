package com.github.hualuomoli.config.app;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySource(value = { //
		"classpath:prop/jdbc.properties", //
		"file:path/to/test", // 测试环境绝对路径
		"file:path/to/publish", // 生产环境绝对路径
}, ignoreResourceNotFound = true)
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		logger.info("instance dataSource.");
		if (logger.isDebugEnabled()) {
			logger.debug("driverClassName {}", driverClassName);
			logger.debug("url {}", url);
			logger.debug("username {}", username);
			logger.debug("password {}", password);
		}

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
