package com.github.hualuomoli.plugins.cache.memcached;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context-memcached.xml" })
public class MemcachedClientServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MemcachedClientService.class);
	private static final String key = "username";

	@Autowired
	private MemcachedClientService memcachedClientService;

	@Test
	public void test() throws Exception {
		boolean result = false;
		String data = null;
		result = memcachedClientService.set(key, 1, "hualuomoli");
		logger.debug("set username {}", result);
		data = memcachedClientService.get(key);
		logger.debug("get username {}", data);
		Thread.sleep(200);
		data = memcachedClientService.get(key);
		logger.debug("get username {}", data);
		Thread.sleep(1200);
		data = memcachedClientService.get(key);
		logger.debug("get username {}", data);

	}

}
