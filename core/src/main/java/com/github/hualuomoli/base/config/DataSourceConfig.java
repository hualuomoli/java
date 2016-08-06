package com.github.hualuomoli.base.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.hualuomoli.commons.util.YamlUtils;

@Configuration
// 加载配置文件
// @propertysource(value = { //
// "classpath:prop/jdbc.properties", //
// "file:path/to/test", // 测试环境绝对路径
// "file:path/to/publish", // 生产环境绝对路径
// }, ignoreResourceNotFound = true)
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	private static final DB db = YamlUtils.getInstance().getObject("jdbc", DB.class);

	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		logger.info("instance dataSource.");
		if (logger.isDebugEnabled()) {
			logger.debug("driverClassName {}", db.driverClassName);
			logger.debug("url {}", db.url);
			logger.debug("username {}", db.username);
			logger.debug("password {}", db.password);
		}

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(db.driverClassName);
		dataSource.setUrl(db.url);
		dataSource.setUsername(db.username);
		dataSource.setPassword(db.password);

		return dataSource;
	}

	// spring加载配置文件
	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static class DB {

		private String driverClassName;
		private String url;
		private String username;
		private String password;

		public DB() {
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
