package com.github.hualuomoli.base.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.hualuomoli.base.plugin.mybatis.dialect.db.MySQLDialect;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.ResourceUtils;
import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;

/**
 * Mybatis配置
 * @author hualuomoli
 *
 */
@Configuration
@Import({ DataSourceConfig.class })
public class MybatisConfig {

	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	private String mapperLocations = YamlUtils.getInstance().getString("mybatis", "mapperLocations");
	private List<String> typeHandlerClasses = YamlUtils.getInstance().getList("typeHandlers", String.class, "mybatis");

	@Resource(name = "dataSource")
	DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean loadSqlSessionFactoryBean() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (logger.isInfoEnabled()) {
			logger.info("mapperLocations {}", mapperLocations);
		}

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(ResourceUtils.loadPattern(mapperLocations));

		// 自定义处理解析类
		if (typeHandlerClasses != null && typeHandlerClasses.size() > 0) {
			List<TypeHandler<?>> typeHandlers = Lists.newArrayList();
			for (String typeHandlerClass : typeHandlerClasses) {
				if (logger.isDebugEnabled()) {
					logger.debug("typeHandler={}", typeHandlerClass);
				}
				typeHandlers.add((TypeHandler<?>) Class.forName(typeHandlerClass).newInstance());
			}
			sqlSessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler<?>[] {}));
		}

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
		if (logger.isInfoEnabled()) {
			logger.info("instance transactionManager.");
		}

		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);

		return transactionManager;
	}

}
