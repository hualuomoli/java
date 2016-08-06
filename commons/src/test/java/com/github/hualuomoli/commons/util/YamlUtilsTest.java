package com.github.hualuomoli.commons.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.hualuomoli.commons.util.YamlConfig.Config;

public class YamlUtilsTest {

	@Test
	public void testGetValue() {
		Config config = YamlConfig.load("test1.yaml");
		// value
		String name = config.getString("project", "name");
		Assert.assertEquals("commons", name);
	}

	@Test
	public void testGetConfigValue() {
		Config config = YamlConfig.load("test1.yaml");
		// value
		String name = config.getString("project", "name");
		Assert.assertEquals("commons", name);
		// object
		User user = config.getObject("user", User.class, "project");
		Assert.assertEquals("hualuomoli", user.getUsername());
		Assert.assertEquals("花落莫离", user.getNickname());
		// list
		List<Server> serverList = config.getList("servers", Server.class, "project");
		Assert.assertEquals(2, serverList.size());
		Assert.assertEquals(1001, serverList.get(0).getPort().intValue());
		Assert.assertEquals("http://localhost", serverList.get(0).getAddress());
		Assert.assertEquals(1002, serverList.get(1).getPort().intValue());
		Assert.assertEquals("http://www.baidu.com", serverList.get(1).getAddress());

	}

	@Test
	public void testGetFiles() {
		Config config = YamlConfig.load("test1.yaml", "test2.yaml");
		// data in test2
		String location = config.getString("project", "location");
		Assert.assertEquals("E:/projects/commons", location);

		// 重写 test1中的值
		String name = config.getString("project", "name");
		Assert.assertEquals("hualuomoli", name);

		// 原 test1中的数据是否存在
		// object
		User user = config.getObject("user", User.class, "project");
		Assert.assertEquals("hualuomoli", user.getUsername());
		Assert.assertEquals("花落莫离", user.getNickname());
		// list
		// list
		List<Server> serverList = config.getList("servers", Server.class, "project");
		Assert.assertEquals(2, serverList.size());
		Assert.assertEquals(1001, serverList.get(0).getPort().intValue());
		Assert.assertEquals("http://localhost", serverList.get(0).getAddress());
		Assert.assertEquals(1002, serverList.get(1).getPort().intValue());
		Assert.assertEquals("http://www.baidu.com", serverList.get(1).getAddress());
	}

}
