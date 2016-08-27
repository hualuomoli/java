package com.github.hualuomoli.mybatis.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.github.hualuomoli.lang.PriceStr;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class PriceStrTypeHandler extends BaseTypeHandler<PriceStr> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, PriceStr parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.parseString());
	}

	@Override
	public PriceStr getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		return this.newInstance(value);
	}

	@Override
	public PriceStr getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return this.newInstance(value);
	}

	@Override
	public PriceStr getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return this.newInstance(value);
	}

	private PriceStr newInstance(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new PriceStr(value);
	}

}
