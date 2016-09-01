package com.github.hualuomoli.plugin.roll.entity;

import org.apache.ibatis.type.JdbcType;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.BaseField;

/**
 * 轮询数据
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "轮询数据")
public class PollData extends BaseField {

	public static final Integer DATA_TYPE_STRING = 1;
	public static final Integer DATA_TYPE_BYTES = 2;
	public static final Integer DATA_TYPE_SERIAL = 3;

	@EntityColumn(comment = "数据ID")
	@EntityColumnQuery(inArray = true)
	private String dataId;
	@EntityColumn(comment = "数据类型 1=字符串,2=字节,3=序列化数据")
	private Integer dataType;
	@EntityColumn(comment = "字符串数据")
	private String stringData;
	@EntityColumn(comment = "字节数据", type = JdbcType.BLOB)
	private byte[] byteData;
	@EntityColumn(comment = "处理者类名", length = 200, nullable = false)
	private String dealerClassName;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public byte[] getByteData() {
		return byteData;
	}

	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}

	public String getDealerClassName() {
		return dealerClassName;
	}

	public void setDealerClassName(String dealerClassName) {
		this.dealerClassName = dealerClassName;
	}

}
