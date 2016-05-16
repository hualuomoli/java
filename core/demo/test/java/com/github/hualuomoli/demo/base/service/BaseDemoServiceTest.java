package com.github.hualuomoli.demo.base.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

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
		BaseDemo baseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(baseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

	@Test
	public void test01Insert() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setId(id);
		baseDemo.setName("花落莫离");
		demoService.insert(baseDemo);
	}

	@Test
	public void test02GetBaseDemo() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setId(id);
		baseDemo = demoService.get(baseDemo);
		Assert.assertNotNull(baseDemo);
		Assert.assertEquals("花落莫离", baseDemo.getName());
	}

	@Test
	public void test03Update() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setId(id);
		baseDemo.setName("修改名称");
		baseDemo.setAge(20);
		demoService.update(baseDemo);
	}

	@Test
	public void test04GetString() {
		BaseDemo baseDemo = demoService.get(id);
		Assert.assertNotNull(baseDemo);
		Assert.assertEquals("修改名称", baseDemo.getName());
		Assert.assertSame(20, baseDemo.getAge());
	}

	@Test
	public void test05DeleteBaseDemo() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setId(id);
		demoService.delete(baseDemo);
		baseDemo = demoService.get(id);
		Assert.assertNull(baseDemo);
	}

	@Test
	public void test06Insert() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setId(id);
		baseDemo.setName("花落莫离");
		demoService.insert(baseDemo);
	}

	@Test
	public void test07DeleteString() {
		demoService.delete(id);
		BaseDemo baseDemo = demoService.get(id);
		Assert.assertNull(baseDemo);
	}

	@Test
	public void test08BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseDemo baseDemo = new BaseDemo();
			baseDemo.setId(String.valueOf(i));
			baseDemo.setName(RandomUtils.getString());
			list.add(baseDemo);
		}
		demoService.batchInsert(list);

		list = demoService.findList(new BaseDemo());
		Assert.assertSame(20, list.size());
	}

	@Test
	public void test09DeleteByIdsStringArray() {
		String[] ids = new String[20];
		for (int i = 0; i < 20; i++) {
			ids[i] = String.valueOf(i);
		}
		demoService.deleteByIds(ids);

		List<BaseDemo> list = demoService.findList(new BaseDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test10BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			BaseDemo baseDemo = new BaseDemo();
			baseDemo.setId(String.valueOf(i));
			baseDemo.setName(RandomUtils.getString());
			list.add(baseDemo);
		}
		demoService.batchInsert(list);
	}

	@Test
	public void test11DeleteByIdsCollectionOfString() {
		List<String> ids = Lists.newArrayList();
		for (int i = 0; i < 20; i++) {
			ids.add(String.valueOf(i));
		}
		demoService.deleteByIds(ids);

		List<BaseDemo> list = demoService.findList(new BaseDemo());
		Assert.assertSame(0, list.size());
	}

	@Test
	public void test12BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			BaseDemo baseDemo = new BaseDemo();
			baseDemo.setId(String.valueOf(i));
			baseDemo.setName("name" + (i % 3));
			baseDemo.setAge(20 + (i % 4));
			list.add(baseDemo);
		}
		demoService.batchInsert(list);
	}

	@Test
	public void test13FindList() {
		List<BaseDemo> list = null;
		BaseDemo baseDemo = null;

		// 1
		baseDemo = new BaseDemo();
		baseDemo.setName("name0");
		list = demoService.findList(baseDemo);
		Assert.assertSame(34, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setName("name1");
		list = demoService.findList(baseDemo);
		Assert.assertSame(33, list.size());

		//
		baseDemo = new BaseDemo();
		baseDemo.setAge(20);
		list = demoService.findList(baseDemo);
		Assert.assertSame(25, list.size());

		// 3*4*8=96
		baseDemo = new BaseDemo();
		baseDemo.setName("name0");
		baseDemo.setAge(20);
		list = demoService.findList(baseDemo);
		Assert.assertSame(9, list.size());

		baseDemo = new BaseDemo();
		baseDemo.setName("name2");
		baseDemo.setAge(23);
		list = demoService.findList(baseDemo);
		Assert.assertSame(8, list.size());

	}

	@Test
	public void test14Clear() {
		BaseDemo baseDemo = new BaseDemo();
		List<BaseDemo> list = demoService.findList(baseDemo);
		List<String> ids = Lists.newArrayList();
		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

	@Test
	public void test15BatchInsert() {
		List<BaseDemo> list = Lists.newArrayList();
		boolean b = false;
		for (int i = 0; i < 100; i++) {
			b = !b;
			BaseDemo baseDemo = new BaseDemo();
			baseDemo.setId(String.valueOf(i));
			baseDemo.setName(b ? "jack" : "tony");
			baseDemo.setAge(20 + (i % 3));
			list.add(baseDemo);
		}
		demoService.batchInsert(list);
	}

	@Test
	public void test16FindPage() {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setName("jack");
		baseDemo.setAge(20);

		// total = 6 * 16 = 96
		Page page = demoService.findPage(baseDemo, new Pagination(3, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		page = demoService.findPage(baseDemo, new Pagination(4, 5));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(2, page.getDataList().size());

		// order

		// no order
		page = demoService.findPage(baseDemo, 3, 5);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by str array
		// 1
		page = demoService.findPage(baseDemo, 3, 5, "name desc,id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(baseDemo, 3, 5, "name desc", "id asc");
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 3
		page = demoService.findPage(baseDemo, new Pagination(3, 5, "name desc"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Ojbect array
		// 1
		page = demoService.findPage(baseDemo, 3, 5, new Order("salary", Direction.DESC), new Order("sex"));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		page = demoService.findPage(baseDemo, new Pagination(3, 5, new Order("salary", Direction.DESC)));
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());

		// order by Ojbect list
		// 1
		List<Order> list1 = Lists.newArrayList();
		list1.add(new Order("birth_day", Direction.DESC));
		list1.add(new Order("age"));
		page = demoService.findPage(baseDemo, 3, 5, list1);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
		// 2
		List<Order> list2 = Lists.newArrayList();
		list2.add(new Order("birth_day", Direction.DESC));
		page = demoService.findPage(baseDemo, 3, 5, list2);
		Assert.assertSame(17, page.getCount());
		Assert.assertSame(5, page.getDataList().size());
	}

	@Test
	public void test99Clear() {
		List<BaseDemo> list = demoService.findList(new BaseDemo());
		List<String> ids = Lists.newArrayList();

		for (BaseDemo demo : list) {
			ids.add(demo.getId());
		}
		demoService.deleteByIds(ids);
	}

}