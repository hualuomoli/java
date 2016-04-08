package com.github.hualuomoli.plugins.jpush.ios;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JPushIOSTest {

	@Test
	public void test() {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("type", "1");
		extras.put("id", "1234567890");
		extras.put("max", "1");
		extras.put("firstPrize", "1200");
		extras.put("secPrize", "800");
		extras.put("thridPrize", "300");
		extras.put("forthPrize", "100");
		extras.put("desc", "大奖等着你，快来试试手气。");

		String alert = "提示信息";

		new JPushIOS() {
		}.pushNotification(alert, null, extras);

		System.out.println("send ok");
	}

}
