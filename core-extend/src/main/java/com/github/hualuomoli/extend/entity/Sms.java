package com.github.hualuomoli.extend.entity;

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;

@SuppressWarnings("serial")
@EntityTable(comment = "短信")
public class Sms extends CommonField {

	@EntityColumn(comment = "手机号码", nullable = false)
	private String phone;
	@EntityColumn(comment = "验证码", nullable = false)
	private String checkCode;
	@EntityColumn(comment = "短信内容", length = 200, nullable = false)
	private String content;
	@EntityColumn(comment = "短信类别", length = 200, nullable = false)
	private Integer type;
	@EntityColumn(comment = "短信有效时长中文描述")
	private String validTime;
	@EntityColumn(comment = "短信有效终止时间")
	@EntityColumnQuery(greaterThan = true, greaterEqual = true, lessThan = true, lessEqual = true)
	private Date validDate;
	@EntityColumn(comment = "短信状态")
	private Integer state;
	@EntityColumn(comment = "短信状态描述")
	private String stateName;

	public Sms() {
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
