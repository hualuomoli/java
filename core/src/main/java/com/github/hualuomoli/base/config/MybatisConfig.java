package com.github.hualuomoli.base.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.hualuomoli.base.YamlBaseConfig;
import com.github.hualuomoli.base.plugin.mybatis.dialect.db.MySQLDialect;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.ResourceUtils;

/**
 * Mybatis配置
 * @author hualuomoli
 *
 */
@Configuration
@Import({ DataSourceConfig.class })
public class MybatisConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	private String mapperLocations = YamlBaseConfig.getValue("mybatis", "mapperLocations");

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

}
