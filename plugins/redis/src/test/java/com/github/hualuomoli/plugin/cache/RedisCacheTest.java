package com.github.hualuomoli.plugin.cache;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.commons.util.YamlUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母排序
public class RedisCacheTest {

	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static final String key = "key";

	private static RedisCache cache;

	@BeforeClass
	public static void beforeClass() {
		Redis redis = YamlUtils.getInstance().getObject("redis", Redis.class);
		// 连接redis服务器
		Jedis jedis = new Jedis(redis.host, Integer.parseInt(redis.port));
		// 权限认证
		if (StringUtils.isNotBlank(redis.password)) {
			jedis.auth(redis.password);
		}
		cache = new RedisCache();
		cache.setPrefix("_redis");
		cache.setJedis(jedis);

		logger.debug("{}", jedis);
	}

	@Test
	public void test01Init() {
		cache.empty();
	}

	@Test
	public void test02Success() {
		boolean success = cache.success();
		Assert.assertTrue(success);
	}

	@Test
	public void test03Init() {
		cache.empty();
	}

	@Test
	public void test04SetStringByteArray() {
		String value = "this is cache message,这是一段缓存信息";
		boolean success = cache.set(key, value.getBytes(Charset.UTF8));
		Assert.assertTrue(success);
		byte[] bs = cache.get(key);
		Assert.assertEquals(value, new String(bs, Charset.UTF8));
	}

	@Test
	public void test05SetStringByteArrayInt() throws InterruptedException {
		int expire = (int) (Math.random() * 5) + 1;
		String value = "this is cache message,这是一段缓存信息";
		boolean success = cache.set(key, value.getBytes(Charset.UTF8), expire);
		Assert.assertTrue(success);
		byte[] bs = cache.get(key);
		Assert.assertEquals(value, new String(bs, Charset.UTF8));
		// 设置超时
		Thread.sleep(expire * 1000);
		bs = cache.get(key);
		Assert.assertNull(bs);
	}

	@Test
	public void test06SetStringSerializable() {
		boolean success = false;
		// string
		String str = "this is cache message,这是一段缓存信息";
		success = cache.setSerializable(key, str);
		Assert.assertTrue(success);
		String _str = cache.getSerializable(key);
		Assert.assertEquals(str, _str);
		// Integer
		Integer i = (int) (Math.random() * 1000);
		success = cache.setSerializable(key, i);
		Assert.assertTrue(success);
		Integer _i = cache.getSerializable(key);
		Assert.assertEquals(i, _i);
		// Long
		Long l = (long) (Math.random() * 100000000000L);
		success = cache.setSerializable(key, l);
		Assert.assertTrue(success);
		Long _l = cache.getSerializable(key);
		Assert.assertEquals(l, _l);
		// Double
		Double d = Math.random();
		success = cache.setSerializable(key, d);
		Assert.assertTrue(success);
		Double _d = cache.getSerializable(key);
		Assert.assertEquals(d, _d);

		// Map
		HashMap<String, String> map = Maps.newHashMap();
		map.put("username", "hualuomoli");
		map.put("nickname", "花落莫离");
		success = cache.setSerializable(key, map);
		Assert.assertTrue(success);
		HashMap<String, String> _map = cache.getSerializable(key);
		Assert.assertTrue(StringUtils.equals(JsonUtils.toJson(map), JsonUtils.toJson(_map)));

		// list
		ArrayList<String> list = Lists.newArrayList();
		list.add("nodejs");
		list.add("java");
		list.add("php");
		list.add(".net");
		success = cache.setSerializable(key, list);
		Assert.assertTrue(success);
		ArrayList<String> _list = cache.getSerializable(key);
		Assert.assertTrue(StringUtils.equals(JsonUtils.toJson(list), JsonUtils.toJson(_list)));
		//
		// Serializable Object
		User user = new User("hualuomoli", "花落莫离", "山东省青岛市");
		success = cache.setSerializable(key, user);
		Assert.assertTrue(success);
		User _user = cache.getSerializable(key);
		Assert.assertTrue(StringUtils.equals(JsonUtils.toJson(user), JsonUtils.toJson(_user)));
	}

	@Test
	public void test07SetStringSerializableInt() throws InterruptedException {
		int expire = (int) (Math.random() * 3) + 1;

		boolean success = false;
		User user = new User("hualuomoli", "花落莫离", "山东省青岛市");
		success = cache.setSerializable(key, user, expire);
		Assert.assertTrue(success);
		User _user = cache.getSerializable(key);
		Assert.assertTrue(StringUtils.equals(JsonUtils.toJson(user), JsonUtils.toJson(_user)));

		Thread.sleep(expire * 1000);
		User __user = cache.getSerializable(key);
		Assert.assertNull(__user);
	}

	@Test
	public void test08Get() {
		String value = "this is cache message,这是一段缓存信息";
		boolean success = cache.setSerializable(key, value);
		Assert.assertTrue(success);
		String _value = cache.getSerializable(key);
		Assert.assertEquals(value, _value);
	}

	@Test
	public void test09GetSerializable() throws InterruptedException {
		User user = new User("hualuomoli", "花落莫离", "山东省青岛市");
		boolean success = cache.setSerializable(key, user);
		Assert.assertTrue(success);
		User _user = cache.getSerializable(key);
		Assert.assertTrue(StringUtils.equals(JsonUtils.toJson(user), JsonUtils.toJson(_user)));
	}

	@Test
	public void test10Remove() {
		String value = "this is cache message,这是一段缓存信息";
		boolean success = false;
		success = cache.setSerializable(key, value);
		Assert.assertTrue(success);
		success = cache.remove(key);
		Assert.assertTrue(success);
		String _value = cache.getSerializable(key);
		Assert.assertNull(_value);
	}

	@Test
	public void test11Exists() {
		String value = "this is cache message,这是一段缓存信息";
		boolean success = false;
		success = cache.setSerializable(key, value);
		Assert.assertTrue(success);
		success = cache.exists(key);
		Assert.assertTrue(success);
		String _value = cache.getSerializable(key);
		Assert.assertEquals(value, _value);
		success = cache.remove(key);
		Assert.assertTrue(success);
		success = cache.exists(key);
		Assert.assertTrue(!success);
		String __value = cache.getSerializable(key);
		Assert.assertNull(__value);
	}

	@Test
	public void test12Plus() {
		Long number = (long) (Math.random() * 10000000L) + 10000000;
		Long init = (long) (Math.random() * 1000000L) + 100;
		Long count = cache.plus(key, init);
		Assert.assertEquals(init, count);
		Long _count = cache.plus(key, number);
		Assert.assertEquals((Long) (number + init), _count);
	}

	@Test
	public void test13Empty() {
		cache.empty();
	}

	public static final class Redis {
		private String host;
		private String port;
		private String password;

		public Redis() {
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
