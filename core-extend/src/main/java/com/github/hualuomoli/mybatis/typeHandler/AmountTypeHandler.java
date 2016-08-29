package com.github.hualuomoli.mybatis.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.github.hualuomoli.lang.Amount;

@MappedJdbcTypes(value = { JdbcType.BIGINT }, includeNullJdbcType = true)
public class AmountTypeHandler extends BaseTypeHandler<Amount> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Amount parameter, JdbcType jdbcType) throws SQLException {
		ps.setLong(i, parameter.parseLong());
	}

	@Override
	public Amount getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Long value = rs.getLong(columnName);
		return this.newInstance(value);
	}

	@Override
	public Amount getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Long value = rs.getLong(columnIndex);
		return this.newInstance(value);
	}

	@Override
	public Amount getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Long value = cs.getLong(columnIndex);
		return this.newInstance(value);
	}

	private Amount newInstance(Long value) {
		if (value == null) {
			return null;
		}
		return new Amount(value);
	}

}
