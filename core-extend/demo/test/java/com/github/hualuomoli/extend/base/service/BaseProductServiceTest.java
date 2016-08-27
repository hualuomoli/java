package com.github.hualuomoli.extend.base.service;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.extend.base.entity.BaseProduct;
import com.github.hualuomoli.lang.Amount;
import com.github.hualuomoli.lang.Price;
import com.github.hualuomoli.lang.PriceStr;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class BaseProductServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseProductService baseProductService;
	private static final String id = "1";

	@Test
	public void test00Clear() {
		BaseProduct baseProduct = new BaseProduct();
		List<BaseProduct> list = baseProductService.findList(baseProduct);
		List<String> ids = Lists.newArrayList();
		for (BaseProduct baseProduct2 : list) {
			ids.add(baseProduct2.getId());
		}
		baseProductService.deleteByIds(ids);
	}

	@Test
	public void test01Insert() {
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setId(id);
		baseProduct.setName("产品");
		baseProduct.setDescription("描述信息");
		baseProduct.setPrice(new Price("30.06"));
		baseProduct.setPriceStr(new PriceStr("66.35"));
		baseProduct.setAmount(new Amount("123456.789"));

		int count = baseProductService.insert(baseProduct);
		Assert.assertEquals("insert error.", 1, count);
	}

	@Test
	public void test02Update() {
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setId(id);
		baseProduct.setName("test测试");
		baseProduct.setPrice(new Price("66.05"));
		baseProduct.setPriceStr(new PriceStr("30.26"));
		baseProduct.setAmount(new Amount("9876.265"));
		baseProductService.update(baseProduct);

		baseProduct = baseProductService.get(id);
		Assert.assertEquals("test测试", baseProduct.getName());
		Assert.assertEquals(new Price("66.05"), baseProduct.getPrice());
		Assert.assertEquals(new PriceStr("30.26"), baseProduct.getPriceStr());
		Assert.assertEquals(new Amount("9876.265"), baseProduct.getAmount());

	}

	@Test
	public void test03Get() {
		BaseProduct baseProduct = baseProductService.get(id);
		Assert.assertEquals("test测试", baseProduct.getName());
		Assert.assertEquals(new Price("66.05"), baseProduct.getPrice());
		Assert.assertEquals(new PriceStr("30.26"), baseProduct.getPriceStr());
		Assert.assertEquals(new Amount("9876.265"), baseProduct.getAmount());
	}

	@Test
	public void test04Query() {
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setPrice(new Price("66.05"));
		List<BaseProduct> list = baseProductService.findList(baseProduct);
		for (BaseProduct baseProduct2 : list) {
			logger.debug("baseProduct {}", ToStringBuilder.reflectionToString(baseProduct2));
		}
	}

}
