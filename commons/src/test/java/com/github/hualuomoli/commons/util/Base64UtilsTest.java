package com.github.hualuomoli.commons.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64UtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

	@Test
	public void test() {
		String testData = "这是一段中文文字，测试编码和解码";
		String origin = "";
		for (int i = 0; i < 10; i++) {
			origin += testData + "\n";
		}
		String encode = Base64Utils.encodeBase64String(origin);
		logger.debug("encode {}", encode);
		origin = Base64Utils.decodeBase64String(encode);
		logger.debug("origin {}", origin);
	}

	@Test
	public void testLocation() {
		String testData = "E:/github/hualuomoli/java/commons/src/test/java/com/github/hualuomoli/commons/util/Base64UtilsTest.java";
		String origin = "http://localhost:80/ausyri/ueditor/file/";
		for (int i = 0; i < 10; i++) {
			origin = testData;
		}
		String encode = Base64Utils.encodeBase64String(origin);
		logger.debug("encode {}", encode);
		origin = Base64Utils.decodeBase64String(encode);
		logger.debug("origin {}", origin);
	}

}
