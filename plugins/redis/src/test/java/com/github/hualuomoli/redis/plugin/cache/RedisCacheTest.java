package com.github.hualuomoli.redis.plugin.cache;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.plugin.cache.redis.RedisCache;
import com.github.hualuomoli.redis.plugin.cache.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;

public class RedisCacheTest {

	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static final String key = "key";
	private static final String value = "this is cache message";

	private static RedisCache cache;

	@BeforeClass
	public static void beforeClass() {
		// 连接redis服务器
		Jedis jedis = new Jedis("localhost", 6379);
		// 权限认证
		// jedis.auth("admin");
		cache = new RedisCache();
		cache.setJedis(jedis);

		logger.debug("{}", jedis);
	}

	@Test
	public void testSomeType() {
		boolean success = false;
		Object v;
		// set
		success = cache.set(key, value);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());
		// use this key set type to int
		success = cache.set(key, 1);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());
		// use this key set type to double
		success = cache.set(key, 1D);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());

		// use this key set type to long
		success = cache.set(key, 1L);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());

		// use this key set type to map
		HashMap<String, String> map = Maps.newHashMap();
		map.put("username", "hualuomoli");
		map.put("nickname", "花落莫离");
		success = cache.set(key, map);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());

		// use this key set type to list
		ArrayList<String> list = Lists.newArrayList();
		list.add("nodejs");
		list.add("java");
		list.add("php");
		list.add(".net");
		success = cache.set(key, list);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());

		// use this key set type to object
		User user = new User("hualuomoli", "花落莫离", "山东省青岛市");
		success = cache.set(key, user);
		logger.debug("set {} '{}'", key, success);
		v = cache.get(key);
		logger.debug(key + " = {} {}", v, v.getClass().getName());

	}

	@Test
	public void testExpire() throws InterruptedException {
		boolean success = false;
		int seconds = 1;
		Object v;
		success = cache.set(key, value, seconds);
		logger.debug("set  use {} seconds '{}'", seconds, success);
		v = cache.get(key);
		logger.debug("{} in expire {}", key, v);
		Thread.sleep((seconds + 1) * 1000);
		logger.debug("imitate time out");
		v = cache.get(key);
		logger.debug("{} out expire {}", key, v);
	}

	@Test
	public void testExists() {
		boolean success = false;
		success = cache.set(key, value);
		logger.debug("set {} '{}'", key, success);
		success = cache.exists(key);
		logger.debug("{} exists '{}'", key, success);
		success = cache.remove(key);
		logger.debug("remove {} '{}'", key, success);
		success = cache.exists(key);
		logger.debug("{} exists '{}'", key, success);
	}

	@Test
	public void setPlus() {
		long v;
		// remove it before test
		cache.remove(key);
		logger.debug("{} exists '{}'", key, cache.exists(key));

		// init plus
		v = cache.plus(key, 1);
		logger.debug("plus not exists {} add 1, return data is {}", key, v);

		// plus
		v = cache.plus(key, 1);
		logger.debug("plus exists {} add 1, return data is {}", key, v);

		// plus 100
		v = cache.plus(key, 100);
		logger.debug("plus exists {} add 100, return data is {}", key, v);

	}

	@Test
	public void testEmpty() {
		cache.set(key, value);
		logger.debug("{} exists {} ", key, cache.get(key));
		// empty
		cache.empty();
		logger.debug("empty cache");
		logger.debug("{} exists {} ", key, cache.get(key));
	}

	@Test
	public void testPersistent() throws InterruptedException {
		cache.set(key, value);
		logger.debug("{} exists {} ", key, cache.get(key));

		// imitate restart
		logger.debug("imitate restart");
		Thread.sleep(2 * 1000);
		beforeClass();
		logger.debug("{} exists {} ", key, cache.get(key));
		logger.debug("{} is {}", key, cache.get(key));
	}

}
