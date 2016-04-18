package com.github.hualuomoli.base.mybatis.interceptor.pagination;

import java.sql.Connection;
import java.util.Map;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.github.hualuomoli.commons.util.Reflections;
import com.github.hualuomoli.base.Paginator;
import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.mybatis.interceptor.BaseInterceptor;

/**
 * 数据库分页插件，只拦截查询语句.
 * @author ThinkGem
 * @version 2015-1-14
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 153424789364381545L;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		// 获取分页参数对象
		Pagination pagination = null;
		if (parameter != null) {
			pagination = getPagination(parameter);
		}

		// 如果设置了分页对象，则进行分页
		if (pagination != null) {

			String originalSql = boundSql.getSql().trim();

			// 得到总记录数
			Executor exec = (Executor) invocation.getTarget();
			Connection conn = exec.getTransaction().getConnection();
			pagination.setCount(PaginationSQLHelper.getCount(originalSql, conn, mappedStatement, parameter, boundSql, logger));

			// 分页查询 本地化对象 修改数据库注意修改实现
			String pageSql = PaginationSQLHelper.generatePageSql(originalSql, pagination, dialect);
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			// 解决MyBatis 分页foreach 参数失效 start
			if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
				Reflections.setFieldValue(newBoundSql, "metaParameters", mo);
			}
			// 解决MyBatis 分页foreach 参数失效 end
			MappedStatement newMs = PaginationSQLHelper.createStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

			invocation.getArgs()[0] = newMs;
		}

		// execute
		return invocation.proceed();
	}

	/**
	 * 对参数进行转换和检查
	 * @param parameterObject 参数对象
	 * @param page            分页对象
	 * @return 分页对象
	 * @throws NoSuchFieldException 无法找到参数
	 */
	@SuppressWarnings("rawtypes")
	protected Pagination getPagination(Object parameter) {
		try {
			// Paginator
			if (parameter instanceof Paginator) {
				return ((Paginator) parameter).getPagination();
			}
			// map
			if (parameter instanceof Map) {
				Map tempMap = (Map) parameter;
				if (tempMap.containsKey(PAGINATION)) {
					return (Pagination) tempMap.get(PAGINATION);
				} else {
					return null;
				}
			}
			// get field which name is pagination
			// return (Pagination) Reflections.getFieldValue(parameter, PAGINATION);
		} catch (Exception e) {
			logger.warn("there has a error.", e);
		}
		return null;
	}

}
