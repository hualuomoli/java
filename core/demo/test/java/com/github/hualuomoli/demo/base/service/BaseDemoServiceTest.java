package com.github.hualuomoli.demo.base.service;

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
import com.github.hualuomoli.demo.base.entity.BaseDemo;
import com.github.hualuomoli.test.AbstractContextServiceTest;
import com.google.common.collect.Lists;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class BaseDemoServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseDemoService demoService;
	private static String id = "1";

	@Test
	public void test00Clear() {
		BaseDemo BaseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(BaseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

	@Test
	public void test01Insert() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		BaseDemo.setName("花落莫离");
		Integer count = demoService.insert(BaseDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test02GetBaseDemo() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		BaseDemo = demoService.get(BaseDemo);
		Assert.assertNotNull(BaseDemo);
		Assert.assertEquals("花落莫离", BaseDemo.getName());
	}

	@Test
	public void test03Update() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		BaseDemo.setName("修改名称");
		BaseDemo.setAge(20);
		Integer count = demoService.update(BaseDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test04GetString() {
		BaseDemo BaseDemo = demoService.get(id);
		Assert.assertNotNull(BaseDemo);
		Assert.assertEquals("修改名称", BaseDemo.getName());
		Assert.assertSame(20, BaseDemo.getAge());
	}

	@Test
	public void test050LogicDeleteBaseDemo() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		BaseDemo.setName("hualuomoli");
		Integer count = demoService.logicalDelete(BaseDemo);
		Assert.assertSame(1, count);
		BaseDemo = demoService.get(id);
		Assert.assertNotNull(BaseDemo);
		Assert.assertEquals(Status.DELETED.getValue(), BaseDemo.getStatus());
	}

	@Test
	public void test05DeleteBaseDemo() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		Integer count = demoService.delete(BaseDemo);
		Assert.assertSame(1, count);
		BaseDemo = demoService.get(id);
		Assert.assertNull(BaseDemo);
	}

	@Test
	public void test06Insert() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setId(id);
		BaseDemo.setName("花落莫离");
		Integer count = demoService.insert(BaseDemo);
		Assert.assertSame(1, count);
	}

	@Test
	public void test07DeleteString() {
		Integer count = demoService.delete(id);
		Assert.assertSame(1, count);
		BaseDemo BaseDemo = demoService.get(id);
		Assert.assertNull(BaseDemo);
	}

	@Test
	public void test08BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseDemo BaseDemo = new BaseDemo();
			BaseDemo.setId(String.valueOf(i));
			BaseDemo.setName(RandomUtils.getUUID());
			list.add(BaseDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(20, count);

		list = demoService.findList(new BaseDemo());
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

		List<BaseDemo> list = demoService.findList(new BaseDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test10BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseDemo BaseDemo = new BaseDemo();
			BaseDemo.setId(String.valueOf(i));
			BaseDemo.setName(RandomUtils.getUUID());
			list.add(BaseDemo);
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

		List<BaseDemo> list = demoService.findList(new BaseDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test12BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			BaseDemo BaseDemo = new BaseDemo();
			BaseDemo.setId(String.valueOf(i));
			BaseDemo.setName("name" + (i % 3));
			BaseDemo.setAge(20 + (i % 4));
			list.add(BaseDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(100, count);
	}

	@Test
	public void test13FindList() {
		List<BaseDemo> list = null;
		BaseDemo BaseDemo = null;

		// 1
		BaseDemo = new BaseDemo();
		BaseDemo.setName("name0");
		list = demoService.findList(BaseDemo);
		Assert.assertSame(34, list.size());

		//
		BaseDemo = new BaseDemo();
		BaseDemo.setName("name1");
		list = demoService.findList(BaseDemo);
		Assert.assertSame(33, list.size());

		//
		BaseDemo = new BaseDemo();
		BaseDemo.setAge(20);
		list = demoService.findList(BaseDemo);
		Assert.assertSame(25, list.size());

		// 3*4*8=96
		BaseDemo = new BaseDemo();
		BaseDemo.setName("name0");
		BaseDemo.setAge(20);
		list = demoService.findList(BaseDemo);
		Assert.assertSame(9, list.size());

		BaseDemo = new BaseDemo();
		BaseDemo.setName("name2");
		BaseDemo.setAge(23);
		list = demoService.findList(BaseDemo);
		Assert.assertSame(8, list.size());

		// order
		BaseDemo = new BaseDemo();
		BaseDemo.setName("name0");
		BaseDemo.setAge(20);

		// order by str array
		// 1
		list = demoService.findList(BaseDemo, "name desc,id asc");
		Assert.assertSame(9, list.size());
		// 2
		list = demoService.findList(BaseDemo, "name desc", "id asc");
		Assert.assertSame(9, list.size());

		// order by Ojbect array
		// 1
		list = demoService.findList(BaseDemo, new Order("salary", Direction.DESC), new Order("sex"));
		Assert.assertSame(9, list.size());
		// 2
		list = demoService.findList(BaseDemo, new Order("salary", Direction.DESC));
		Assert.assertSame(9, list.size());

		// order by Object list
		// 1
		List<Order> list1 = Lists.newArrayList();
		list1.add(new Order("birth_day", Direction.DESC));
		list1.add(new Order("age"));
		list = demoService.findList(BaseDemo, list1);
		Assert.assertSame(9, list.size());
		// 2
		List<Order> list2 = Lists.newArrayList();
		list2.add(new Order("birth_day", Direction.DESC));
		list = demoService.findList(BaseDemo, list2);
		Assert.assertSame(9, list.size());
	}

	@Test
	public void test14Clear() {
		BaseDemo BaseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(BaseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(100, count);
	}

	@Test
	public void test15BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		boolean b = false;
		for (int i = 0; i < 100; i++) {
			b = !b;
			BaseDemo BaseDemo = new BaseDemo();
			BaseDemo.setId(String.valueOf(i));
			BaseDemo.setName(b ? "jack" : "tony");
			BaseDemo.setAge(20 + (i % 3));
			list.add(BaseDemo);
		}
		Integer count = demoService.batchInsert(list);
		Assert.assertSame(100, count);
	}

	@Test
	public void test16FindPage() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setName("jack");
		BaseDemo.setAge(20);

		// total = 6 * 16 = 96
		Page page = demoService.findPage(BaseDemo, new Pagination(3, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		page = demoService.findPage(BaseDemo, new Pagination(4, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(2, page.getDataList().size());

		// order

		// no order
		page = demoService.findPage(BaseDemo, 3, 5);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by str array
		// 1
		page = demoService.findPage(BaseDemo, 3, 5, "name desc,id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(BaseDemo, 3, 5, "name desc", "id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 3
		page = demoService.findPage(BaseDemo, new Pagination(3, 5, "name desc"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Ojbect array
		// 1
		page = demoService.findPage(BaseDemo, 3, 5, new Order("salary", Direction.DESC), new Order("sex"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(BaseDemo, new Pagination(3, 5, new Order("salary", Direction.DESC)));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Object list
		// 1
		List<Order> list1 = Lists.newArrayList();
		list1.add(new Order("birth_day", Direction.DESC));
		list1.add(new Order("age"));
		page = demoService.findPage(BaseDemo, 3, 5, list1);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		List<Order> list2 = Lists.newArrayList();
		list2.add(new Order("birth_day", Direction.DESC));
		page = demoService.findPage(BaseDemo, 3, 5, list2);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
	}

	@Test
	public void test17GetTotal() {
		BaseDemo BaseDemo = new BaseDemo();
		BaseDemo.setName("jack");
		BaseDemo.setAge(20);

		Integer count = demoService.getTotal(BaseDemo);
		Assert.assertSame(17, count);

	}

	@Test
	public void test99Clear() {
		List<BaseDemo> list = demoService.findList(new BaseDemo());
		List<String> ids = Lists.newArrayList();

		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		Integer count = demoService.deleteByIds(ids);
		Assert.assertSame(100, count);
	}

}