package com.github.hualuomoli.demo.base.service.orm;

import java.util.List;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.hualuomoli.base.entity.Pagination;
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

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Resource(name = "com.github.hualuomoli.demo.base.service.orm.OrmDemoService")
	private DemoService demoService;

	@Test
	public void testGet() {
		Demo demo = demoService.get("1");
		logger.debug("demo", demo);
	}

	@Test
	public void testInsert() {
		Demo demo = new Demo();
		demo.setName("testme");
		demoService.insert(demo);
	}

	@Test
	public void testUpdate() {
		Demo demo = new Demo();
		demo.setId("1");
		demo.setRemark("修改备注");
		demoService.update(demo);
	}

	@Test
	public void testDelete() {
		demoService.delete("2");
	}

	@Test
	public void testFindList() {
		Demo demo = new Demo();
		demo.setName("testme");
		List<Demo> list = demoService.findList(demo);
		logger.debug("list", list);
	}

	@Test
	public void testFindPage() {
		Demo demo = new Demo();
		demo.setName("testme");
		Pagination pagination = new Pagination(3, 4);
		pagination.setOrderBy("name desc,age asc");
		pagination = demoService.findPage(demo, pagination);
		logger.debug("pagination", pagination);
	}

}
