package com.github.hualuomoli.demo.base.service.orm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.demo.base.service.DemoService;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class) // spring junit 运行环境
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
@ContextConfiguration(locations = { // 加载spring配置文件
		// core
		"classpath:spring/application-context-core.xml", //
		// orm
		"classpath:orm/spring/application-context-jdbc.xml", //
		"classpath:orm/spring/application-context-orm.xml", //
		"classpath:orm/spring/application-context-jdbc-init.xml" //
})
public class DemoServiceImplTest {

	private static final String id = "1";

	@Resource(name = "com.github.hualuomoli.demo.base.service.orm.DemoServiceImpl")
	private DemoService ormDemoService;

	@Test
	public void test01Insert() {
		Demo demo = new Demo();
		demo.setId(id);
		demo.setName("testme");
		demo.setSex("M");
		ormDemoService.insert(demo);
	}

	@Test
	public void test02GetDemo() {
		Demo demo = new Demo();
		demo.setId(id);
		demo = ormDemoService.get(demo);
		Assert.assertNotNull(demo);
	}

	@Test
	public void test02GetString() {
		Demo demo = ormDemoService.get(id);
		Assert.assertNotNull(demo);
	}

	@Test
	public void test03Update() {
		Demo demo = new Demo();
		demo.setId(id);
		demo = ormDemoService.get(demo);
		Assert.assertNotNull(demo);

		// update
		demo.setRemark("添加注释");
		ormDemoService.update(demo);

		// get
		demo = ormDemoService.get(demo);
		Assert.assertNotNull(demo);
		Assert.assertEquals(demo.getId(), id);
		Assert.assertEquals(demo.getName(), "testme");
		Assert.assertEquals(demo.getSex(), "M");
		Assert.assertNull(demo.getAge());
		Assert.assertEquals(demo.getRemark(), "添加注释");
	}

	@Test
	public void test04Update() {

		Demo demo = new Demo();
		demo.setId(id);

		// update
		demo.setSalary(1234.56);
		ormDemoService.update(demo);

		// get
		demo = ormDemoService.get(demo);
		Assert.assertNotNull(demo);
		Assert.assertEquals(demo.getId(), id);
		Assert.assertEquals(demo.getName(), "testme");
		Assert.assertEquals(demo.getSex(), "M");
		Assert.assertNull(demo.getAge());
		Assert.assertEquals(demo.getRemark(), "添加注释");
		Assert.assertEquals(demo.getSalary(), Double.valueOf(1234.56));
	}

	@Test
	public void test05DeleteDemo() {
		Demo demo = new Demo();
		demo.setId(id);
		// delte
		ormDemoService.delete(demo);
		// get
		demo = ormDemoService.get(id);
		Assert.assertNull(demo);
	}

	@Test
	public void test06Insert() {
		Demo demo = new Demo();
		demo.setId(id);
		demo.setName("testme");
		demo.setSex("F");
		ormDemoService.insert(demo);
	}

	@Test
	public void test07DeleteString() {
		// delte
		ormDemoService.delete(id);
		// get
		Demo demo = ormDemoService.get(id);
		Assert.assertNull(demo);
	}

	@Test
	public void test08BatchInsert() {
		int max = 100;
		List<Demo> list = Lists.newArrayList();
		String sex = "F";
		for (int i = 0; i < max; i++) {
			Demo demo = new Demo();
			demo.setId(String.valueOf(i));
			demo.setName("testme");
			demo.setAge(i);
			demo.setSex(sex);
			sex = StringUtils.equals(sex, "F") ? "M" : "F";

			list.add(demo);

		}
		ormDemoService.batchInsert(list);
	}

	@Test
	public void test09DeleteByIdsStringArray() {
		String[] ids = new String[] { "1", "3", "5" };
		ormDemoService.deleteByIds(ids);
		Demo demo = ormDemoService.get("3");
		Assert.assertNull(demo);
	}

	@Test
	public void test10DeleteByIdsCollectionOfString() {
		List<String> ids = Lists.newArrayList(new String[] { "2", "4", "6" });
		ormDemoService.deleteByIds(ids);
		Demo demo = ormDemoService.get("6");
		Assert.assertNull(demo);
	}

	@Test
	public void test11FindList() {
		Demo demo = new Demo();
		demo.setSex("F");
		List<Demo> list = ormDemoService.findList(demo);
		Assert.assertEquals(list.size(), 47);
	}

	@Test
	public void test12FindPage() {
		Demo demo = new Demo();
		demo.setSex("M");

		Pagination pagination = new Pagination(3, 20);

		pagination = ormDemoService.findPage(demo, pagination);
		Assert.assertNotNull(pagination.getDataList());
		Assert.assertTrue(pagination.getDataList().size() == 7);
	}

	@Test
	public void testClear() {
		List<Demo> list = ormDemoService.findList(new Demo());
		String[] ids = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getId();
		}
		ormDemoService.deleteByIds(ids);

		list = ormDemoService.findList(new Demo());
		Assert.assertTrue(list.size() == 0);
	}

}
