package com.github.hualuomoli.base.demo.service;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.base.demo.entity.Demo;
import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.junit.SpringJunitTest;

public class DemoServiceTest extends SpringJunitTest {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Autowired
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
