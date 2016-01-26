package com.github.hualuomoli.modules.base.mybatis.interceptor.pagination;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 保存和获取SqlSource
 * @author liubaoquan
 *
 */
public class BoundSqlSqlSource implements SqlSource {

	BoundSql boundSql;

	public BoundSqlSqlSource(BoundSql boundSql) {
		this.boundSql = boundSql;
	}

	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}

}
