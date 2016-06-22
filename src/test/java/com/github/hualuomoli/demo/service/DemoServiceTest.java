package com.github.hualuomoli.demo.service;

import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.entity.Demo;
import com.github.hualuomoli.test.AnnotationConfigTest;

public class DemoServiceTest extends AnnotationConfigTest {

	private static DemoService demoService;

	@BeforeClass
	public static void beforeClass() {
		demoService = rootContext.getBean(DemoService.class);
	}

	@Test
	public void test() {

		Demo demo = new Demo();
		demo.setId(UUID.randomUUID().toString());
		demo.setName("hualuomoli");
		demoService.insert(demo);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPage() {
		Demo demo = new Demo();
		demo.setOrderByStr("id");
		List<Demo> list = demoService.findList(demo);
		logger.debug("size {}", list.size());
		Page page = demoService.findPage(demo, 2, 3);
		logger.debug("count ", page.getCount());
		list = page.getDataList();
		logger.debug("size {}", list.size());
		for (Demo demo2 : list) {
			logger.debug(demo2.getId());
		}
	}

}
