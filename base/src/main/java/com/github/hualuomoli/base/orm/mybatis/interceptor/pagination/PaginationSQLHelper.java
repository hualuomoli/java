package com.github.hualuomoli.base.orm.mybatis.interceptor.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.orm.mybatis.dialect.Dialect;
import com.github.hualuomoli.commons.util.Reflections;

/**
 * SQL工具类
 * @author liubaoquan
 *
 */
public class PaginationSQLHelper {

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps              表示预编译的 SQL 语句的对象。
	 * @param mappedStatement MappedStatement
	 * @param boundSql        SQL
	 * @param parameterObject 参数对象
	 * @throws java.sql.SQLException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					@SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	/**
	 * 查询总纪录数
	 * @param sql             SQL语句
	 * @param connection      数据库连接
	 * @param mappedStatement mapped
	 * @param parameterObject 参数
	 * @param boundSql        boundSql
	 * @return 总记录数
	 * @throws SQLException sql查询错误
	 */
	public static int getCount(final String sql, final Connection connection, final MappedStatement mappedStatement, final Object parameterObject,
			final BoundSql boundSql, final Logger logger) throws SQLException {
		final String countSql = "select count(1) from (" + removeOrders(sql) + ") tmp_count";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("COUNT SQL: " + StringUtils.replaceEach(countSql, new String[] { "\n", "\t" }, new String[] { " ", " " }));
			}
			if (connection != null) {
				ps = connection.prepareStatement(countSql);
			} else {
				conn = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
				ps = conn.prepareStatement(countSql);
			}
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
			// 解决MyBatis 分页foreach 参数失效 start
			if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
				Reflections.setFieldValue(countBS, "metaParameters", mo);
			}
			// 解决MyBatis 分页foreach 参数失效 end
			setParameters(ps, mappedStatement, countBS, parameterObject);
			rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql     Mapper中的Sql语句
	 * @param page    分页对象
	 * @param dialect 方言类型
	 * @return 分页SQL
	 */
	public static String generatePageSql(String sql, Pagination pagination, Dialect dialect) {
		if (dialect.supportsLimit()) {
			int offset = (pagination.getPageNo() - 1) * pagination.getPageSize();
			int limit = pagination.getPageSize();
			return dialect.getLimitString(sql, offset, limit);
		} else {
			return sql;
		}
	}

	/** 
	 * 去除qlString的select子句。 
	 * @param hql 
	 * @return 
	 */
	@SuppressWarnings("unused")
	private String removeSelect(String qlString) {
		int beginPos = qlString.toLowerCase().indexOf("from");
		return qlString.substring(beginPos);
	}

	/** 
	 * 去除hql的orderBy子句。 
	 * @param hql 
	 * @return 
	 */
	private static String removeOrders(String qlString) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(qlString);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 根据SqlSource创建新的MappedStatement
	 * @param ms MappedStatement
	 * @param newSqlSource SqlSource
	 * @return 新的MappedStatement
	 */
	public static MappedStatement createStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null) {
			for (String keyProperty : ms.getKeyProperties()) {
				builder.keyProperty(keyProperty);
			}
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		return builder.build();
	}

}
