package com.github.hualuomoli.demo.creator.base.entity;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.Persistent;

public class BaseDemo extends com.github.hualuomoli.demo.entity.Demo implements Persistent {

	/**  - 左like */
	private java.lang.String nameLeftLike;
	/**  - 右like */
	private java.lang.String nameRightLike;
	/**  - 左右like */
	private java.lang.String nameBothLike;
	/**  - 查询数组 */
	private java.lang.String[] nameArray;
	/** 工资 - 大于 */
	private java.lang.Double salaryGreaterThan;
	/** 工资 - 大于等于 */
	private java.lang.Double salaryGreaterEqual;
	/** 工资 - 小于 */
	private java.lang.Double salaryLessThan;
	/** 工资 - 小于等于 */
	private java.lang.Double salaryLessEqual;
	/** 工资 - 查询数组 */
	private java.lang.Double[] salaryArray;
	/** 年龄 - 大于 */
	private java.lang.Integer ageGreaterThan;
	/** 年龄 - 大于等于 */
	private java.lang.Integer ageGreaterEqual;
	/** 年龄 - 小于 */
	private java.lang.Integer ageLessThan;
	/** 年龄 - 小于等于 */
	private java.lang.Integer ageLessEqual;
	/** 年龄 - 查询数组 */
	private java.lang.Integer[] ageArray;
	/** 生日 - 大于 */
	private java.util.Date birthDayGreaterThan;
	/** 生日 - 大于等于 */
	private java.util.Date birthDayGreaterEqual;
	/** 生日 - 小于 */
	private java.util.Date birthDayLessThan;
	/** 生日 - 小于等于 */
	private java.util.Date birthDayLessEqual;
	/** 用户 - 查询数组 */
	private java.lang.String[] userUsernameArray;
	
	public BaseDemo(){
	}
	
	public BaseDemo(java.lang.String name){
		this.setName(name);
	}
	
	public java.lang.String getNameLeftLike() {
		return nameLeftLike;
	}
	
	public void setNameLeftLike(java.lang.String nameLeftLike) {
		if (StringUtils.isBlank(nameLeftLike)) {
			return;
		}
		this.nameLeftLike = nameLeftLike + "%";
	}
	public java.lang.String getNameRightLike() {
		return nameRightLike;
	}
	
	public void setNameRightLike(java.lang.String nameRightLike) {
		if (StringUtils.isBlank(nameRightLike)) {
			return;
		}
		this.nameRightLike = "%" + nameRightLike;
	}
	public java.lang.String getNameBothLike() {
		return nameBothLike;
	}
	
	public void setNameBothLike(java.lang.String nameBothLike) {
		if (StringUtils.isBlank(nameBothLike)) {
			return;
		}
		this.nameBothLike = "%" + nameBothLike + "%";
	}
	public java.lang.String[] getNameArray() {
		return nameArray;
	}
	
	public void setNameArray(java.lang.String[] nameArray) {
		if (nameArray == null || nameArray.length == 0) {
			return;
		}
		this.nameArray = nameArray;
	}
	public java.lang.Double getSalaryGreaterThan() {
		return salaryGreaterThan;
	}
	
	public void setSalaryGreaterThan(java.lang.Double salaryGreaterThan) {
		this.salaryGreaterThan = salaryGreaterThan;
	}
	public java.lang.Double getSalaryGreaterEqual() {
		return salaryGreaterEqual;
	}
	
	public void setSalaryGreaterEqual(java.lang.Double salaryGreaterEqual) {
		this.salaryGreaterEqual = salaryGreaterEqual;
	}
	public java.lang.Double getSalaryLessThan() {
		return salaryLessThan;
	}
	
	public void setSalaryLessThan(java.lang.Double salaryLessThan) {
		this.salaryLessThan = salaryLessThan;
	}
	public java.lang.Double getSalaryLessEqual() {
		return salaryLessEqual;
	}
	
	public void setSalaryLessEqual(java.lang.Double salaryLessEqual) {
		this.salaryLessEqual = salaryLessEqual;
	}
	public java.lang.Double[] getSalaryArray() {
		return salaryArray;
	}
	
	public void setSalaryArray(java.lang.Double[] salaryArray) {
		if (salaryArray == null || salaryArray.length == 0) {
			return;
		}
		this.salaryArray = salaryArray;
	}
	public java.lang.Integer getAgeGreaterThan() {
		return ageGreaterThan;
	}
	
	public void setAgeGreaterThan(java.lang.Integer ageGreaterThan) {
		this.ageGreaterThan = ageGreaterThan;
	}
	public java.lang.Integer getAgeGreaterEqual() {
		return ageGreaterEqual;
	}
	
	public void setAgeGreaterEqual(java.lang.Integer ageGreaterEqual) {
		this.ageGreaterEqual = ageGreaterEqual;
	}
	public java.lang.Integer getAgeLessThan() {
		return ageLessThan;
	}
	
	public void setAgeLessThan(java.lang.Integer ageLessThan) {
		this.ageLessThan = ageLessThan;
	}
	public java.lang.Integer getAgeLessEqual() {
		return ageLessEqual;
	}
	
	public void setAgeLessEqual(java.lang.Integer ageLessEqual) {
		this.ageLessEqual = ageLessEqual;
	}
	public java.lang.Integer[] getAgeArray() {
		return ageArray;
	}
	
	public void setAgeArray(java.lang.Integer[] ageArray) {
		if (ageArray == null || ageArray.length == 0) {
			return;
		}
		this.ageArray = ageArray;
	}
	public java.util.Date getBirthDayGreaterThan() {
		return birthDayGreaterThan;
	}
	
	public void setBirthDayGreaterThan(java.util.Date birthDayGreaterThan) {
		this.birthDayGreaterThan = birthDayGreaterThan;
	}
	public java.util.Date getBirthDayGreaterEqual() {
		return birthDayGreaterEqual;
	}
	
	public void setBirthDayGreaterEqual(java.util.Date birthDayGreaterEqual) {
		this.birthDayGreaterEqual = birthDayGreaterEqual;
	}
	public java.util.Date getBirthDayLessThan() {
		return birthDayLessThan;
	}
	
	public void setBirthDayLessThan(java.util.Date birthDayLessThan) {
		this.birthDayLessThan = birthDayLessThan;
	}
	public java.util.Date getBirthDayLessEqual() {
		return birthDayLessEqual;
	}
	
	public void setBirthDayLessEqual(java.util.Date birthDayLessEqual) {
		this.birthDayLessEqual = birthDayLessEqual;
	}
	public java.lang.String[] getUserUsernameArray() {
		return userUsernameArray;
	}
	
	public void setUserUsernameArray(java.lang.String[] userUsernameArray) {
		if (userUsernameArray == null || userUsernameArray.length == 0) {
			return;
		}
		this.userUsernameArray = userUsernameArray;
	}

}
