package com.github.hualuomoli.lang;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 数值
 * @author hualuomoli
 *
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public abstract class Number<T extends Number> implements Serializable {

	private Long entire; // 整数部分
	private Integer decimal; // 小数部分

	public Number() {
		this.entire = 0L;
		this.decimal = 0;
	}

	public Number(String data) {
		if (StringUtils.isBlank(data)) {
			return;
		}
		int index = data.indexOf(".");
		if (index == -1) {
			this.entire = Long.parseLong(data);
			this.decimal = 0;
		} else {
			this.entire = Long.parseLong(data.substring(0, index));
			this.decimal = Integer.parseInt(data.substring(index + 1));
		}
	}

	public Number(Long data) {
		if (data == null) {
			return;
		}
		this.entire = data / this.scalData();
		this.decimal = (int) (data % this.scalData());
	}

	public Number(Long entire, Integer decimal) {
		this.entire = entire;
		this.decimal = decimal;
	}

	public Long getEntire() {
		return entire;
	}

	public void setEntire(Long entire) {
		this.entire = entire;
	}

	public Integer getDecimal() {
		return decimal;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	protected int scal() {
		return 2;
	}

	protected int scalData() {
		return 100;
	}

	// 加
	public T add(T other) {
		if (other == null) {
			return (T) this;
		}

		this.entire += other.getEntire();
		this.decimal += other.getDecimal();

		if (this.decimal > this.scalData()) {
			this.entire += 1;
			this.decimal -= this.scalData();
		}

		return (T) this;
	}

	// 减
	public T minus(T other) {
		if (other == null) {
			return (T) this;
		}

		this.entire -= other.getEntire();
		this.decimal -= other.getDecimal();

		if (this.decimal < 0) {
			this.entire -= 1;
			this.decimal += this.scalData();
		}

		return (T) this;
	}

	// 转换成string
	public String parseString() {
		if (decimal == null || decimal == 0) {
			return String.valueOf(this.entire);
		}
		return String.valueOf(this.entire) + "." + this.getDecimalValue();
	}

	// 转换成Long
	public Long parseLong() {
		return this.entire * this.scalData() + this.decimal;
	}

	// 小数部分的值
	private String getDecimalValue() {
		// 前补零
		String data = String.valueOf(this.decimal);
		for (int i = 0; i < this.scal(); i++) {
			data = "0" + data;
		}
		return data.substring(data.length() - this.scal());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Number) {
			Number n = (Number) obj;
			return this.getEntire() - n.getEntire() == 0 && this.getDecimal() - n.getDecimal() == 0;
		}
		return false;
	}

}
