package com.github.hualuomoli.raml.parser.entity;

import java.util.List;

/**
 * 参数
 * @author hualuomoli
 *
 */
public class Param {

	private String displayName; // 显示名称
	private String description; // 描述
	private ParamType paramType; // 参数类型
	private boolean required; // 是否必填
	private String example; // 参考例子

	// private boolean repeat;
	private List<String> enumeration; // 常量
	private String pattern; // 正则表达式
	private Integer minLength; // 最小长度
	private Integer maxLength; // 最大长度
	private String minimum; // 最小值
	private String maximum; // 最大值
	private String defaultValue; // 默认值

	public Param() {
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ParamType getParamType() {
		return paramType;
	}

	public void setParamType(ParamType paramType) {
		this.paramType = paramType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public List<String> getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(List<String> enumeration) {
		this.enumeration = enumeration;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
