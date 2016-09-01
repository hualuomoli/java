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
@EntityTable(comment = "轮询失败历史")
public class PollErrorHis extends BaseField {

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
	@EntityColumn(comment = "调度频度 ,使用逗号(,)分割(s=秒,m=分钟,h=小时,d=天)", length = 200, nullable = false)
	private String frequency;
	@EntityColumn(comment = "优先级,值越大越优先", name = "priority")
	private Integer priority;

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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
