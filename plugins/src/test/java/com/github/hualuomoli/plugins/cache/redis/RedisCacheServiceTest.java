package com.github.hualuomoli.plugins.cache.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context-redis.xml" })
public class RedisCacheServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RedisCacheService.class);
	@Autowired
	private RedisCacheService redisCacheService;

	@Test
	public void test() throws Exception {
		String key = "hualuomoli花落莫离";

		String strValue = "This is String data.";
		logger.debug("cache started {}", redisCacheService.isOk());
		logger.debug("set string {}", redisCacheService.set(key, strValue));
		logger.debug("get key {}", redisCacheService.get(key));

		logger.debug("set string 5 second {}", redisCacheService.set(key, strValue, 5));
		Thread.sleep(6000);
		logger.debug("sleep 6 second. get key {}", redisCacheService.get(key));

	}

}
