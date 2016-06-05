package com.github.hualuomoli.plugin.cache.memcached;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // spring junit 运行环境
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
@ContextConfiguration(locations = { // 加载spring配置文件
		// core
		"classpath:spring/application-context-memcached.xml", //
})
public class MemcachedCacheTest {

	@Autowired
	private MemcachedCache memcachedCache;

	private static String count = "count";
	private static String key = "demo";
	private static Demo demo = new Demo();

	@BeforeClass
	public static void beforeClass() {
		demo.setAge(21);
		demo.setBirthDay(new Date());
		demo.setName("hualuomoli");
		demo.setSex("M");
	}

	@Test
	public void test01IsInstance() {
		Assert.assertTrue(memcachedCache.isInstance());
	}

	@Test
	public void test02SetStringSerializable() {
		boolean success = memcachedCache.set(key, demo);
		Assert.assertTrue(success);
		Demo d = memcachedCache.get(key);
		Assert.assertNotNull(d);
	}

	@Test
	public void test03SetStringSerializableInt() throws InterruptedException {
		int total = 5;
		int first = 2;
		boolean success = memcachedCache.set(key, demo, total);
		Assert.assertTrue(success);
		Thread.sleep(first * 1000);
		Demo d = memcachedCache.get(key);
		Assert.assertNotNull(d);
		Thread.sleep((total - first) * 1000);
		d = memcachedCache.get(key);
		Assert.assertNull(d);
	}

	@Test
	public void test04Remove() {
		boolean success = memcachedCache.set(key, demo);
		Assert.assertTrue(success);
		Demo d = memcachedCache.get(key);
		Assert.assertNotNull(d);
		memcachedCache.remove(key);
		d = memcachedCache.get(key);
		Assert.assertNull(d);
	}

	@Test
	public void test06Exists() {
		boolean success = memcachedCache.set(key, demo);
		Assert.assertTrue(success);
		success = memcachedCache.exists(key);
		Assert.assertTrue(success);
	}

	@Test
	public void test07Plus() {
		memcachedCache.remove(count);
		long c = memcachedCache.plus(count, 1);
		Assert.assertEquals(1, c);
		c = memcachedCache.plus(count, 100);
		Assert.assertEquals(101, c);
	}

	@Test
	public void testEmpty() {
		boolean success = memcachedCache.set(key, demo);
		Assert.assertTrue(success);
		success = memcachedCache.exists(key);
		Assert.assertTrue(success);
		memcachedCache.empty();
		success = memcachedCache.exists(key);
		Assert.assertTrue(!success);
	}

}
