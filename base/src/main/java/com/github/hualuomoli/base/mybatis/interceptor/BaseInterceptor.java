package com.github.hualuomoli.base.mybatis.interceptor;

import java.io.Serializable;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.base.mybatis.dialect.Dialect;
import com.github.hualuomoli.base.mybatis.dialect.db.MySQLDialect;

/**
 * Mybatis拦截器基类
 * @author ThinkGem
 * @version 2015-1-14
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {

	private static final long serialVersionUID = 7834896333921319820L;

	protected static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

	protected static final String PAGINATION = "pagination";
	protected static final String DIALECT = "dialect";
	protected static final String DELEGATE = "delegate";
	protected static final String MAPPED_STATEMENT = "mappedStatement";

	protected Properties properties;
	protected Dialect dialect;

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
		// instance dialect
		this.setDialect();
	}

	// set dialect
	private void setDialect() {
		if (this.properties.contains(DIALECT)) {
			try {
				this.dialect = (Dialect) Class.forName(this.properties.getProperty(DIALECT)).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.dialect = new MySQLDialect();
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

}
