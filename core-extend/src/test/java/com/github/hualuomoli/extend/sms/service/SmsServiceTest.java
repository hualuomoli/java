package com.github.hualuomoli.extend.sms.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.extend.base.entity.BaseSms;
import com.github.hualuomoli.extend.base.service.BaseSmsService;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class SmsServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseSmsService baseSmsService;
	@Autowired
	private SmsService smsService;

	@Test
	public void test00Clear() {
		List<BaseSms> list = baseSmsService.findList(new BaseSms());
		List<String> ids = Lists.newArrayList();
		for (BaseSms baseSms : list) {
			ids.add(baseSms.getId());
		}
		baseSmsService.deleteByIds(ids);
	}

	@Test
	public void test01SaveStringStringIntegerString() {
		// 初始发送
		smsService.save("15689952699", "1234", 1, "欢迎使用平台注册,您的手机号码是:phone,短信验证码:checkCode,有效时间:validTime");
	}

	@Test
	public void test02SaveStringStringIntegerString() {
		// 再次发送
		smsService.save("15689952699", "5678", 1, "欢迎使用平台注册,您的手机号码是:phone,短信验证码:checkCode,有效时间:validTime");
	}

	@Test
	public void test03SaveStringStringIntegerIntegerString() {
		// 设置有效时间
		smsService.save("15689952699", "abcd", 2, 1, "欢迎使用平台注册,您的手机号码是:phone,短信验证码:checkCode,有效时间:validTime");
	}

	@Test
	public void test04ValidStringStringInteger() {
		// 类型错误
		boolean success = smsService.valid("15689952699", "1234", 2);
		Assert.assertFalse(success);
	}

	@Test
	public void test05ValidStringStringInteger() {
		// 已失效
		boolean success = smsService.valid("15689952699", "1234", 1);
		Assert.assertFalse(success);
	}

	@Test
	public void test06ValidStringStringInteger() {
		// 正常
		boolean success = smsService.valid("15689952699", "5678", 1);
		Assert.assertTrue(success);
	}

	@Test
	public void test07ValidStringStringIntegerInteger() throws InterruptedException {
		// 有效时间内
		boolean success = smsService.valid("15689952699", "abcd", 2);
		Assert.assertTrue(success);
		// 超时
		Thread.sleep(1000);
		success = smsService.valid("15689952699", "abcd", 2);
		Assert.assertFalse(success);
	}

	@Test
	public void test99Clear() {
		List<BaseSms> list = baseSmsService.findList(new BaseSms());
		List<String> ids = Lists.newArrayList();
		for (BaseSms baseSms : list) {
			ids.add(baseSms.getId());
		}
		baseSmsService.deleteByIds(ids);
	}

}
