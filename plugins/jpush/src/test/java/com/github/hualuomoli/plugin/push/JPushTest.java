package com.github.hualuomoli.plugin.push;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class JPushTest {

	@Test
	public void test() {
		JpushService service = new JpushService();
		Map<String, String> extras = Maps.newHashMap();
		extras.put("extras", "{\"type\": 1}");
		service.pushAllNotification("news", "提示", "标题", extras);
	}

}
