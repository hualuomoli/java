package com.github.hualuomoli.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.base.util.BaseUtils.ListSplit;
import com.github.hualuomoli.demo.entity.CreatorDemo;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { //
		"classpath:spring/application-context-jdbc.xml", //
		"classpath:spring/application-context-core.xml", //
		"classpath:spring/application-context-orm.xml" //
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatorDemoServiceTest {

	@Autowired
	private CreatorDemoService creatorDemoService;

	private static final String id = BaseUtils.getRandomId();
	private static final int batchSize = BaseUtils.getBatchMaxCount() * 3;

	@Test
	public void test0Clean() {
		List<CreatorDemo> list = creatorDemoService.findList(new CreatorDemo());
		List<String> ids = Lists.newArrayList();
		for (CreatorDemo creatorDemo : list) {
			ids.add(creatorDemo.getId());
		}
		creatorDemoService.deleteByIds(ids);
	}

	@Test
	public void test1Insert() throws ParseException {
		CreatorDemo creatorDemo = new CreatorDemo();
		creatorDemo.setId(id);
		creatorDemo.setName("hualuomoli");
		creatorDemo.setSex("M");
		creatorDemo.setAge(20);
		creatorDemo.setSalary(20000d);
		creatorDemo.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
		creatorDemo.setSeconds(System.currentTimeMillis());
		creatorDemoService.insert(creatorDemo);
	}

	@Test
	public void test1BatchInsert() throws ParseException {
		List<CreatorDemo> list = Lists.newArrayList();
		for (int i = 0; i < batchSize + 1; i++) {
			CreatorDemo creatorDemo = new CreatorDemo();
			creatorDemo.setName("hualuomoli");
			creatorDemo.setSex("M");
			creatorDemo.setAge(20);
			creatorDemo.setSalary(20000d);
			creatorDemo.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));
			creatorDemo.setSeconds(System.currentTimeMillis());

			list.add(creatorDemo);
		}
		creatorDemoService.batchInsert(list);

	}

	@Test
	public void test2Get() {
		CreatorDemo creatorDemo = creatorDemoService.get(id);
		Assert.notNull(creatorDemo);
	}

	@Test
	public void test3FindList() {
		List<CreatorDemo> list = creatorDemoService.findList(new CreatorDemo());
		Assert.isTrue(list.size() == batchSize + 2);
	}

	@Test
	public void test4Update() {
		CreatorDemo creatorDemo = new CreatorDemo();
		creatorDemo.setId(id);
		creatorDemo.setAge(18);
		creatorDemoService.update(creatorDemo);
		creatorDemo = creatorDemoService.get(id);
		Assert.isTrue(creatorDemo.getAge() == 18);
		Assert.isTrue(StringUtils.equals(creatorDemo.getName(), "hualuomoli"));
	}

	@Test
	public void test5Delete() {
		creatorDemoService.delete(id);
		CreatorDemo creatorDemo = creatorDemoService.get(id);
		Assert.isNull(creatorDemo);
	}

	@Test
	public void test6DeleteByIdsStringArray() {
		List<CreatorDemo> list = creatorDemoService.findList(new CreatorDemo());
		List<CreatorDemo> newList = BaseUtils.getList(list, new ListSplit(10));
		String[] ids = new String[newList.size()];
		for (int i = 0; i < newList.size(); i++) {
			ids[i] = newList.get(i).getId();
		}
		creatorDemoService.deleteByIds(ids);
		CreatorDemo creatorDemo = creatorDemoService.get(ids[0]);
		Assert.isNull(creatorDemo);
	}

	@Test
	public void test7DeleteByIdsCollectionOfString() {
		List<CreatorDemo> list = creatorDemoService.findList(new CreatorDemo());
		List<CreatorDemo> newList = BaseUtils.getList(list, new ListSplit(10));
		List<String> ids = Lists.newArrayList();
		for (CreatorDemo creatorDemo : newList) {
			ids.add(creatorDemo.getId());
		}
		creatorDemoService.deleteByIds(ids);
		CreatorDemo creatorDemo = creatorDemoService.get(ids.get(0));
		Assert.isNull(creatorDemo);
	}

	@Test
	public void test9FindPage() {
		Pagination pagination = new Pagination();
		pagination.setPageNo(3);
		pagination.setPageSize(10);

		CreatorDemo creatorDemo = new CreatorDemo();

		// get 10
		pagination = creatorDemoService.findPage(creatorDemo, pagination);
		Assert.notEmpty(pagination.getDataList());
		Assert.isTrue(pagination.getDataList().size() == pagination.getPageSize());

		// count
		int count = pagination.getCount();

		// get less 10
		pagination.setPageNo(count / pagination.getPageSize() + 1);
		pagination = creatorDemoService.findPage(creatorDemo, pagination);
		Assert.notEmpty(pagination.getDataList());
		Assert.isTrue(pagination.getDataList().size() > 0);
		Assert.isTrue(pagination.getDataList().size() < pagination.getPageSize());

		// get empty
		pagination.setPageNo(pagination.getPageNo() + 1);
		pagination = creatorDemoService.findPage(creatorDemo, pagination);
		Assert.isTrue(pagination.getDataList().size() == 0);
	}

}
