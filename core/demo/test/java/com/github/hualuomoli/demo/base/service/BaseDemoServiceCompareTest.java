package com.github.hualuomoli.demo.base.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.demo.base.entity.BaseDemo;
import com.github.hualuomoli.demo.entity.User;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class BaseDemoServiceCompareTest extends AbstractContextServiceTest {

	@Autowired
	private BaseDemoService demoService;

	@Test
	public void test00Clear() {
		BaseDemo baseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(baseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

	@Test
	public void test01BatchInsert() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseDemo baseDemo = new BaseDemo();
			baseDemo.setId(String.valueOf(i));
			baseDemo.setName("jack" + i);
			baseDemo.setAge(20 + i);
			baseDemo.setBirthDay(sdf.parse(sdf.format(calendar.getTime())));
			User user = new User();
			user.setUsername("tom" + i);
			baseDemo.setUser(user);
			list.add(baseDemo);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		demoService.batchInsert(list);

		list = demoService.findList(new BaseDemo());
		Assert.assertSame(20, list.size());
	}

	@Test
	public void test02String() {
		BaseDemo baseDemo = null;
		List<BaseDemo> list = null;
		//
		baseDemo = new BaseDemo();
		baseDemo.setNameLeftLike("jack");
		list = demoService.findList(baseDemo);
		Assert.assertSame(20, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setNameRightLike("9");
		list = demoService.findList(baseDemo);
		Assert.assertSame(2, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setNameBothLike("k5");
		list = demoService.findList(baseDemo);
		Assert.assertSame(1, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setNameArray(new String[] { "jack5", "jack9" });
		list = demoService.findList(baseDemo);
		Assert.assertSame(2, list.size());

	}

	@Test
	public void test03Number() {
		BaseDemo baseDemo = null;
		List<BaseDemo> list = null;
		//
		baseDemo = new BaseDemo();
		baseDemo.setAgeGreaterEqual(26);
		list = demoService.findList(baseDemo);
		Assert.assertSame(14, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setAgeGreaterThan(26);
		list = demoService.findList(baseDemo);
		Assert.assertSame(13, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setAgeLessThan(26);
		list = demoService.findList(baseDemo);
		Assert.assertSame(6, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setAgeLessEqual(26);
		list = demoService.findList(baseDemo);
		Assert.assertSame(7, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setAgeArray(new Integer[] { 20, 26, 55, 34 });
		list = demoService.findList(baseDemo);
		Assert.assertSame(3, list.size());

	}

	@Test
	public void test04Date() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 开始3天后
		calendar.add(Calendar.DAY_OF_YEAR, 3);
		Date start = sdf.parse(sdf.format(calendar.getTime()));
		// 结束8天后
		calendar.add(Calendar.DAY_OF_YEAR, 8 - 3);
		Date end = sdf.parse(sdf.format(calendar.getTime()));

		BaseDemo baseDemo = null;
		List<BaseDemo> list = null;
		//
		baseDemo = new BaseDemo();
		baseDemo.setBirthDayGreaterEqual(start);
		list = demoService.findList(baseDemo);
		Assert.assertSame(17, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setBirthDayGreaterThan(start);
		list = demoService.findList(baseDemo);
		Assert.assertSame(16, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setBirthDayLessEqual(end);
		list = demoService.findList(baseDemo);
		Assert.assertSame(9, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setBirthDayLessThan(end);
		list = demoService.findList(baseDemo);
		Assert.assertSame(8, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setBirthDayGreaterEqual(start);
		baseDemo.setBirthDayLessThan(end);
		list = demoService.findList(baseDemo);
		Assert.assertSame(5, list.size());

	}

	@Test
	public void test04Entity() {
		BaseDemo baseDemo = null;
		List<BaseDemo> list = null;
		//
		baseDemo = new BaseDemo();
		baseDemo.setUserArray(new String[] { "tom1", "tom2" });
		list = demoService.findList(baseDemo);
		Assert.assertSame(2, list.size());
	}

	@Test
	public void test99Clear() {
		BaseDemo baseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(baseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

}