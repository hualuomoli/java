package com.github.hualuomoli.plugins.cache.memcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context-memcached.xml" })
public class MemcacheCacheServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MemcacheCacheService.class);
	private static final String key = "username";

	@Autowired
	private MemcacheCacheService memcacheCacheService;

	@Test
	public void test() throws Exception {
		boolean result = false;
		String data = null;
		result = memcacheCacheService.set(key, "hualuomoli", 1);
		logger.debug("set username {}", result);
		data = memcacheCacheService.get(key);
		logger.debug("get username {}", data);
		Thread.sleep(200);
		data = memcacheCacheService.get(key);
		logger.debug("get username {}", data);
		Thread.sleep(1200);
		data = memcacheCacheService.get(key);
		logger.debug("get username {}", data);

	}

	@Test
	public void set() throws Exception {
		int max = 100000;
		Bean bean = new Bean();
		bean.setAge(21);
		bean.setName("名字");
		int size = 0;
		boolean success = false;

		long start = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			success = memcacheCacheService.set("key" + i, "this is " + i);
			size += success ? 1 : 0;
			bean.setIndex(i);
			memcacheCacheService.set("bean" + i, bean);
			// logger.debug("set data " + i);
		}
		long end = System.currentTimeMillis();
		logger.debug("size: {} ", size);
		logger.debug("use " + (end - start) / 1000 + "s");
	}

	@Test
	public void get() throws Exception {
		Bean bean = memcacheCacheService.get("bean202");
		logger.debug("" + bean);
	}

	@Test
	public void delete() throws Exception {
		String key = "key20";
		String value = null;
		boolean success = false;

		value = memcacheCacheService.get(key);
		logger.debug("value:{}", value);
		success = memcacheCacheService.delete(key);
		logger.debug("delete:{}", success);
		value = memcacheCacheService.get(key);
		logger.debug("value:{}", value);

	}

	@Test
	public void exists() {
		String key = "key200";
		boolean success = false;

		success = memcacheCacheService.keyExists(key);
		logger.debug("there {} key {}", success ? "has" : "not has", key);
		success = memcacheCacheService.delete(key);
		logger.debug("delete key {} {}", key, success);
		success = memcacheCacheService.keyExists(key);
		logger.debug("there {} key {}", success ? "has" : "not has", key);
	}

	@Test
	public void plus() {
		String key = "number";
		Object value = null;
		// data must be string
		// memcacheCacheService.delete(key);
		long plus = memcacheCacheService.plus(key);
		logger.debug("plus data:{}", plus);
		value = memcacheCacheService.get(key);
		logger.debug("number:{}", value);

		plus = memcacheCacheService.plus(key, 66);
		logger.debug("plus data:{}", plus);
		value = memcacheCacheService.get(key);
		logger.debug("number:{}", value);
	}

}
