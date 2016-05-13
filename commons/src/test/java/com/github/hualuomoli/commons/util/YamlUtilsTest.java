package com.github.hualuomoli.commons.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class YamlUtilsTest {

	@Test
	public void testGetValue() {
		YamlUtils utils = YamlUtils.getInstance("test1.yaml");
		// value
		String name = utils.getValue("project", "name");
		Assert.assertEquals("commons", name);
	}

	@Test
	public void testGetConfigValue() {
		YamlUtils utils = YamlUtils.getInstance("test1.yaml");
		// value
		String name = utils.getConfig("project").getValue("name");
		Assert.assertEquals("commons", name);
		// object
		User user = utils.getConfig("project").getObject("user", User.class);
		Assert.assertEquals("hualuomoli", user.getUsername());
		Assert.assertEquals("花落莫离", user.getNickname());
		// list
		List<Server> serverList = utils.getConfig("project").getList("servers", Server.class);
		Assert.assertEquals(2, serverList.size());
		Assert.assertEquals(1001, serverList.get(0).getPort().intValue());
		Assert.assertEquals("http://localhost", serverList.get(0).getAddress());
		Assert.assertEquals(1002, serverList.get(1).getPort().intValue());
		Assert.assertEquals("http://www.baidu.com", serverList.get(1).getAddress());

	}

	@Test
	public void testGetFiles() {
		YamlUtils utils = YamlUtils.getInstance("test1.yaml", "test2.yaml");
		// data in test2
		String location = utils.getConfig("project").getValue("location");
		Assert.assertEquals("E:/projects/commons", location);

		// 重写 test1中的值
		String name = utils.getConfig("project").getValue("name");
		Assert.assertEquals("hualuomoli", name);

		// 原 test1中的数据是否存在
		// object
		User user = utils.getConfig("project").getObject("user", User.class);
		Assert.assertEquals("hualuomoli", user.getUsername());
		Assert.assertEquals("花落莫离", user.getNickname());
		// list
		// list
		List<Server> serverList = utils.getConfig("project").getList("servers", Server.class);
		Assert.assertEquals(2, serverList.size());
		Assert.assertEquals(1001, serverList.get(0).getPort().intValue());
		Assert.assertEquals("http://localhost", serverList.get(0).getAddress());
		Assert.assertEquals(1002, serverList.get(1).getPort().intValue());
		Assert.assertEquals("http://www.baidu.com", serverList.get(1).getAddress());
	}

}
