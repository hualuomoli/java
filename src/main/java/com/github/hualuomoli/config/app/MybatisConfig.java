package com.github.hualuomoli.config.app;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.hualuomoli.plugin.mybatis.dialect.db.MySQLDialect;
import com.github.hualuomoli.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.util.ResourceUtils;

@Configuration
@PropertySource(value = { //
		"classpath:prop/mybatis.properties", //
		"file:path/to/test", // 测试环境绝对路径
		"file:path/to/publish", // 生产环境绝对路径
}, ignoreResourceNotFound = true)
// 启用注解事务管理，使用CGLib代理
@EnableTransactionManagement(proxyTargetClass = true)
// 依赖数据库配置
@Import({ DataSourceConfig.class })
public class MybatisConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	@Value(value = "${mybatis.mapperLocations}")
	private String mapperLocations;

	@Resource(name = "dataSource")
	DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean loadSqlSessionFactoryBean() throws IOException {
		logger.info("instance SqlSessionFactoryBean.");
		if (logger.isDebugEnabled()) {
			logger.debug("mapperLocations {}", mapperLocations);
		}

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(ResourceUtils.loadPattern(mapperLocations));

		// 分页插件
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置属性
		Properties properties = new Properties();
		paginationInterceptor.setProperties(properties);
		// 设置方言
		paginationInterceptor.setDialect(new MySQLDialect());
		// 添加插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { paginationInterceptor });

		return sqlSessionFactoryBean;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager loadDataSourceTransactionManager() {
		logger.info("instance transactionManager.");

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);

		return transactionManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placehodlerConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
