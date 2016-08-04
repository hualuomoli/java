package com.github.hualuomoli.demo.creator.base.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Direction;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorDemo;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class BaseCreatorDemoServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseCreatorDemoService demoService;
	private static String id = "1";

	@Test
	public void test00Clear() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		List<BaseCreatorDemo> list = demoService.findList(BaseCreatorDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseCreatorDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

	@Test
	public void test01Insert() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		BaseCreatorDemo.setName("花落莫离");
		Integer count = demoService.insert(BaseCreatorDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test02GetBaseCreatorDemo() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		BaseCreatorDemo = demoService.get(BaseCreatorDemo);
		Assert.assertNotNull(BaseCreatorDemo);
		Assert.assertEquals("花落莫离", BaseCreatorDemo.getName());
	}

	@Test
	public void test03Update() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		BaseCreatorDemo.setName("修改名称");
		BaseCreatorDemo.setAge(20);
		Integer count = demoService.update(BaseCreatorDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test04GetString() {
		BaseCreatorDemo BaseCreatorDemo = demoService.get(id);
		Assert.assertNotNull(BaseCreatorDemo);
		Assert.assertEquals("修改名称", BaseCreatorDemo.getName());
		Assert.assertSame(20, BaseCreatorDemo.getAge());
	}

	@Test
	public void test050LogicDeleteBaseCreatorDemo() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		BaseCreatorDemo.setName("hualuomoli");
		Integer count = demoService.logicalDelete(BaseCreatorDemo);
		Assert.assertSame(1, count);
		BaseCreatorDemo = demoService.get(id);
		Assert.assertNotNull(BaseCreatorDemo);
		Assert.assertEquals(Status.DELETED.getValue(), BaseCreatorDemo.getStatus());
	}

	@Test
	public void test05DeleteBaseCreatorDemo() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		Integer count = demoService.delete(BaseCreatorDemo);
		Assert.assertSame(1, count);
		BaseCreatorDemo = demoService.get(id);
		Assert.assertNull(BaseCreatorDemo);
	}

	@Test
	public void test06Insert() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setId(id);
		BaseCreatorDemo.setName("花落莫离");
		Integer count = demoService.insert(BaseCreatorDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test07DeleteString() {
		Integer count = demoService.delete(id);
		Assert.assertSame(1, count);
		BaseCreatorDemo BaseCreatorDemo = demoService.get(id);
		Assert.assertNull(BaseCreatorDemo);
	}

	@Test
	public void test08BatchInsert() {
		List<BaseCreatorDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
			BaseCreatorDemo.setId(String.valueOf(i));
			BaseCreatorDemo.setName(RandomUtils.getUUID());
			list.add(BaseCreatorDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(20, count);

		list = demoService.findList(new BaseCreatorDemo());
		Assert.assertSame(20, list.size());
	}

	@Test
	public void test09DeleteByIdsStringArray() {
		String[] ids = new String[20];
		for (int i = 0; i < 20; i++) {
			ids[i] = String.valueOf(i);
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(20, count);

		List<BaseCreatorDemo> list = demoService.findList(new BaseCreatorDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test10BatchInsert() {
		List<BaseCreatorDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
			BaseCreatorDemo.setId(String.valueOf(i));
			BaseCreatorDemo.setName(RandomUtils.getUUID());
			list.add(BaseCreatorDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(20, count);
	}

	@Test
	public void test11DeleteByIdsCollectionOfString() {
		List<String> ids = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			ids.add(String.valueOf(i));
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(20, count);

		List<BaseCreatorDemo> list = demoService.findList(new BaseCreatorDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test12BatchInsert() {
		List<BaseCreatorDemo> list = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
			BaseCreatorDemo.setId(String.valueOf(i));
			BaseCreatorDemo.setName("name" + (i % 3));
			BaseCreatorDemo.setAge(20 + (i % 4));
			list.add(BaseCreatorDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(100, count);
	}

	@Test
	public void test13FindList() {
		List<BaseCreatorDemo> list = null;
		BaseCreatorDemo BaseCreatorDemo = null;

		// 1
		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("name0");
		list = demoService.findList(BaseCreatorDemo);
		Assert.assertSame(34, list.size());

		//
		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("name1");
		list = demoService.findList(BaseCreatorDemo);
		Assert.assertSame(33, list.size());

		//
		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setAge(20);
		list = demoService.findList(BaseCreatorDemo);
		Assert.assertSame(25, list.size());

		// 3*4*8=96
		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("name0");
		BaseCreatorDemo.setAge(20);
		list = demoService.findList(BaseCreatorDemo);
		Assert.assertSame(9, list.size());

		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("name2");
		BaseCreatorDemo.setAge(23);
		list = demoService.findList(BaseCreatorDemo);
		Assert.assertSame(8, list.size());

		// order
		BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("name0");
		BaseCreatorDemo.setAge(20);

		// order by str array
		// 1
		list = demoService.findList(BaseCreatorDemo, "name desc,id asc");
		Assert.assertSame(9, list.size());
		// 2
		list = demoService.findList(BaseCreatorDemo, "name desc", "id asc");
		Assert.assertSame(9, list.size());

		// order by Ojbect array
		// 1
		list = demoService.findList(BaseCreatorDemo, new Order("salary", Direction.DESC), new Order("sex"));
		Assert.assertSame(9, list.size());
		// 2
		list = demoService.findList(BaseCreatorDemo, new Order("salary", Direction.DESC));
		Assert.assertSame(9, list.size());

		// order by Object list
		// 1
		List<Order> list1 = Lists.newArrayList();
		list1.add(new Order("birth_day", Direction.DESC));
		list1.add(new Order("age"));
		list = demoService.findList(BaseCreatorDemo, list1);
		Assert.assertSame(9, list.size());
		// 2
		List<Order> list2 = Lists.newArrayList();
		list2.add(new Order("birth_day", Direction.DESC));
		list = demoService.findList(BaseCreatorDemo, list2);
		Assert.assertSame(9, list.size());
	}

	@Test
	public void test14Clear() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		List<BaseCreatorDemo> list = demoService.findList(BaseCreatorDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseCreatorDemo demo : list) {
			ids.add(demo.getId());
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(100, count);
	}

	@Test
	public void test15BatchInsert() {
		List<BaseCreatorDemo> list = Lists.newArrayList();
		boolean b = false;
		for (int i = 0; i < 100; i++) {
			b = !b;
			BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
			BaseCreatorDemo.setId(String.valueOf(i));
			BaseCreatorDemo.setName(b ? "jack" : "tony");
			BaseCreatorDemo.setAge(20 + (i % 3));
			list.add(BaseCreatorDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(100, count);
	}

	@Test
	public void test16FindPage() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("jack");
		BaseCreatorDemo.setAge(20);

		// total = 6 * 16 = 96
		Page page = demoService.findPage(BaseCreatorDemo, new Pagination(3, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		page = demoService.findPage(BaseCreatorDemo, new Pagination(4, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(2, page.getDataList().size());

		// order

		// no order
		page = demoService.findPage(BaseCreatorDemo, 3, 5);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by str array
		// 1
		page = demoService.findPage(BaseCreatorDemo, 3, 5, "name desc,id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(BaseCreatorDemo, 3, 5, "name desc", "id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 3
		page = demoService.findPage(BaseCreatorDemo, new Pagination(3, 5, "name desc"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Ojbect array
		// 1
		page = demoService.findPage(BaseCreatorDemo, 3, 5, new Order("salary", Direction.DESC), new Order("sex"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(BaseCreatorDemo, new Pagination(3, 5, new Order("salary", Direction.DESC)));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Object list
		// 1
		List<Order> list1 = Lists.newArrayList();
		list1.add(new Order("birth_day", Direction.DESC));
		list1.add(new Order("age"));
		page = demoService.findPage(BaseCreatorDemo, 3, 5, list1);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		List<Order> list2 = Lists.newArrayList();
		list2.add(new Order("birth_day", Direction.DESC));
		page = demoService.findPage(BaseCreatorDemo, 3, 5, list2);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
	}

	@Test
	public void test17GetTotal() {
		BaseCreatorDemo BaseCreatorDemo = new BaseCreatorDemo();
		BaseCreatorDemo.setName("jack");
		BaseCreatorDemo.setAge(20);

		Integer count = demoService.getTotal(BaseCreatorDemo);
		Assert.assertSame(17, count);

	}

	@Test
	public void test99Clear() {
		List<BaseCreatorDemo> list = demoService.findList(new BaseCreatorDemo());
		List<String> ids = Lists.newArrayList();

		for (BaseCreatorDemo demo : list) {
			ids.add(demo.getId());
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(100, count);
	}

}