package com.github.hualuomoli.mybatis.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.github.hualuomoli.lang.Price;

@MappedJdbcTypes(JdbcType.BIGINT)
public class PriceTypeHandler extends BaseTypeHandler<Price> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Price parameter, JdbcType jdbcType) throws SQLException {
		ps.setLong(i, parameter.parseLong());
	}

	@Override
	public Price getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Long value = rs.getLong(columnName);
		return this.newInstance(value);
	}

	@Override
	public Price getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Long value = rs.getLong(columnIndex);
		return this.newInstance(value);
	}

	@Override
	public Price getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Long value = cs.getLong(columnIndex);
		return this.newInstance(value);
	}

	private Price newInstance(Long value) {
		if (value == null) {
			return null;
		}
		return new Price(value);
	}

}
