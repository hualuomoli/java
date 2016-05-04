package com.github.hualuomoli.demo.base.service.orm;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.demo.base.service.DemoService;

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
public class OrmDemoServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OrmDemoService.class);

	@Resource(name = "com.github.hualuomoli.demo.base.service.orm.OrmDemoService")
	private DemoService ormDemoService;

	@Test
	public void test01Insert() {
		Demo demo = new Demo();
		demo.setName("testme");
		ormDemoService.insert(demo);
	}

	@Test
	public void test01GetDemo() {
	}

	@Test
	public void testGetString() {

	}

	@Test
	public void testBatchInsert() {

	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDeleteDemo() {

	}

	@Test
	public void testDeleteString() {

	}

	@Test
	public void testDeleteByIdsStringArray() {

	}

	@Test
	public void testDeleteByIdsCollectionOfString() {

	}

	@Test
	public void testFindList() {

	}

	@Test
	public void testFindPage() {

	}

}
