package com.github.hualuomoli.commons.http;

import java.io.IOException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class HttpURLUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(HttpURLUtils.class);

	private static String urlStr = "http://localhost:3000/user?";
	private static String data;
	private static Map<String, String> dataMap;

	@BeforeClass
	public static void beforeClass() {

		data = "&username=hualuomoli&password=admin";

		dataMap = Maps.newHashMap();
		dataMap.put("username", "hualuomoli");
		dataMap.put("password", "admin");
	}

	@Test
	public void testDoGetString() {
		String res = HttpURLUtils.doGet(urlStr);
		logger.debug("testDoGetString res {}", res);
	}

	@Test
	public void testDoGetStringString() {
		String res = HttpURLUtils.doGet(urlStr, data);
		logger.debug("testDoGetStringString res {}", res);
	}

	@Test
	public void testDoPostString() {
		String res = HttpURLUtils.doPost(urlStr);
		logger.debug("testDoPostString res {}", res);
	}

	@Test
	public void testDoPostStringMapOfStringQ() {
		String res = HttpURLUtils.doPost(urlStr, dataMap);
		logger.debug("testDoPostStringMapOfStringQ res {}", res);
	}

	@Test
	public void testDoPostStringString() {
		String res = HttpURLUtils.doPost(urlStr, data);
		logger.debug("testDoPostStringString res {}", res);
	}

	@Test
	public void testInvoke() throws IOException {
		String res = HttpURLUtils.invoke(HttpURLUtils.METHOD_POST, urlStr, data, HttpURLUtils.MIME_URLENCODED);
		logger.debug("testDoPostStringString res {}", res);
	}

}
