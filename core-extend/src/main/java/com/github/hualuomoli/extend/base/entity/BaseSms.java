package com.github.hualuomoli.extend.base.entity;

import org.apache.commons.lang3.StringUtils;

public class BaseSms extends com.github.hualuomoli.extend.entity.Sms {

	/** 短信有效终止时间 - 大于 */
	private java.util.Date validDateGreaterThan;
	/** 短信有效终止时间 - 大于等于 */
	private java.util.Date validDateGreaterEqual;
	/** 短信有效终止时间 - 小于 */
	private java.util.Date validDateLessThan;
	/** 短信有效终止时间 - 小于等于 */
	private java.util.Date validDateLessEqual;
	
	public BaseSms(){
	}
	
	
	public java.util.Date getValidDateGreaterThan() {
		return validDateGreaterThan;
	}
	
	public void setValidDateGreaterThan(java.util.Date validDateGreaterThan) {
		this.validDateGreaterThan = validDateGreaterThan;
	}
	public java.util.Date getValidDateGreaterEqual() {
		return validDateGreaterEqual;
	}
	
	public void setValidDateGreaterEqual(java.util.Date validDateGreaterEqual) {
		this.validDateGreaterEqual = validDateGreaterEqual;
	}
	public java.util.Date getValidDateLessThan() {
		return validDateLessThan;
	}
	
	public void setValidDateLessThan(java.util.Date validDateLessThan) {
		this.validDateLessThan = validDateLessThan;
	}
	public java.util.Date getValidDateLessEqual() {
		return validDateLessEqual;
	}
	
	public void setValidDateLessEqual(java.util.Date validDateLessEqual) {
		this.validDateLessEqual = validDateLessEqual;
	}

}
