package com.github.hualuomoli.plugin.mybatis.interceptor.pagination;

import java.sql.Connection;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.github.hualuomoli.commons.util.ReflectionUtils;
import com.github.hualuomoli.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.mybatis.interceptor.BaseInterceptor;

/**
 * 数据库分页插件，只拦截查询语句.
 * @author ThinkGem
 * @version 2015-1-14
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 8824416793231459608L;

	private static final ThreadLocal<Pagination> LOCAL_PAGE = new ThreadLocal<Pagination>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		// 获取分页参数对象
		Pagination pagination = getPagination();

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
			if (ReflectionUtils.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) ReflectionUtils.getFieldValue(boundSql, "metaParameters");
				ReflectionUtils.setFieldValue(newBoundSql, "metaParameters", mo);
			}
			// 解决MyBatis 分页foreach 参数失效 end
			MappedStatement newMs = PaginationSQLHelper.createStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

			invocation.getArgs()[0] = newMs;
		}

		// execute
		return invocation.proceed();
	}

	// 获取分页属性,用于分页判断
	private static Pagination getPagination() {
		return LOCAL_PAGE.get();
	}

	// 设置分页属性,执行分页查询前需要设置
	public static void pushPagination(Pagination pagination) {
		LOCAL_PAGE.set(pagination);
	}

	// 获取分页属性
	public static Pagination popPagination() {
		Pagination pagination = LOCAL_PAGE.get();

		// 获取后,移除分页属性,防止影响下一次查询
		clearPagination();

		return pagination;
	}

	// 清除分页属性,执行分页查询后需要设置
	private static void clearPagination() {
		LOCAL_PAGE.remove();
	}

}
