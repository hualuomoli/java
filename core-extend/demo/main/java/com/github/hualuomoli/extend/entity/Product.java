package com.github.hualuomoli.extend.entity;

import org.apache.ibatis.type.JdbcType;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnCustom;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.entity.CommonField;
import com.github.hualuomoli.lang.Amount;
import com.github.hualuomoli.lang.Price;
import com.github.hualuomoli.lang.PriceStr;
import com.github.hualuomoli.mybatis.typeHandler.AmountTypeHandler;
import com.github.hualuomoli.mybatis.typeHandler.PriceStrTypeHandler;
import com.github.hualuomoli.mybatis.typeHandler.PriceTypeHandler;

@SuppressWarnings("serial")
@EntityTable(comment = "产品")
public class Product extends CommonField {

	private String name;
	private String description;
	@EntityColumn(comment = "价格", type = JdbcType.BIGINT, length = 11)
	@EntityColumnCustom(typeHander = PriceTypeHandler.class)
	private Price price;
	@EntityColumn(comment = "价格字符串", type = JdbcType.VARCHAR, length = 32)
	@EntityColumnCustom(typeHander = PriceStrTypeHandler.class)
	private PriceStr priceStr;
	@EntityColumn(comment = "总金额", type = JdbcType.BIGINT, length = 11)
	@EntityColumnCustom(typeHander = AmountTypeHandler.class)
	private Amount amount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public PriceStr getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(PriceStr priceStr) {
		this.priceStr = priceStr;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

}
