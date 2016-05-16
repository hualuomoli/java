package com.github.hualuomoli.raml.join.adaptor.entity;

import java.math.BigDecimal;
import java.util.List;

import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;

/**
 * 参数数据
 * @author hualuomoli
 *
 */
public class ParamData {

	private DataType dataType; // 数据类型
	private List<ParamData> children; // 子参数集合(dataType 为object和array时设置)

	// raml abstract param
	private String displayName;
	private String description;
	private ParamType type;
	private boolean required;
	// private boolean repeat;
	private List<String> enumeration;
	private String pattern;
	private Integer minLength;
	private Integer maxLength;
	private BigDecimal minimum;
	private BigDecimal maximum;
	private String defaultValue;
	private String example;

	public ParamData() {
	}

	public ParamData(AbstractParam param) {
		this.displayName = param.getDisplayName();
		this.description = param.getDescription();
		this.type = param.getType();
		this.required = param.isRequired();
		this.enumeration = param.getEnumeration();
		this.pattern = param.getPattern();
		this.minLength = param.getMinLength();
		this.maxLength = param.getMaxLength();
		this.minimum = param.getMinimum();
		this.maximum = param.getMaximum();
		this.defaultValue = param.getDefaultValue();
		this.example = param.getExample();
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public List<ParamData> getChildren() {
		return children;
	}

	public void setChildren(List<ParamData> children) {
		this.children = children;
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

	public ParamType getType() {
		return type;
	}

	public void setType(ParamType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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

	public BigDecimal getMinimum() {
		return minimum;
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}

	public void setMaximum(BigDecimal maximum) {
		this.maximum = maximum;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

}
