package com.github.hualuomoli.commons.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigestUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(DigestUtils.class);

	private static final String input = "这是一段需要获取摘要的内容";

	@Test
	public void testEncodeHex() {
		String s2 = null;
		String s1 = null;
		String s3 = null;
		logger.debug("{}", s1 = DigestUtils.encodeHex(DigestUtils.generateSalt(8)));
		logger.debug("{}", s2 = DigestUtils.encodeHex(DigestUtils.generateSalt(16)));
		logger.debug("{}", s3 = DigestUtils.encodeHex(DigestUtils.generateSalt(32)));
		logger.debug("{}", DigestUtils.encodeHex(DigestUtils.generateSalt(5)));

		logger.debug("{}", DigestUtils.decodeHex(s1));
		logger.debug("{}", DigestUtils.decodeHex(s2));
		logger.debug("{}", DigestUtils.decodeHex(s3));

	}

	@Test
	public void testDigest() {
		// 不使用盐
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", null, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", null, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", null, 10));

		// 使用盐
		byte[] salt = DigestUtils.generateSalt(8);
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 10));

		// 返回数据加16位盐
		salt = DigestUtils.generateSalt(8);
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 1));
		logger.debug("null 1 {}", DigestUtils.digest(input, "sha1", salt, 10));

		// 加盐生成数据,验证
		salt = DigestUtils.generateSalt(8);
		String enctypt = DigestUtils.digest(input, "sha1", salt, 10);
		String s = DigestUtils.encodeHex(salt); // 16位盐
		String data = s + enctypt;
		logger.debug("生成密文 {}", data);

		// 验证

		// 根据加盐后的数据获取盐
		String dataSalt = data.substring(0, 16);
		// 根据盐加密验证的原文
		// 正常
		String e = DigestUtils.digest(input, "sha1", DigestUtils.decodeHex(dataSalt), 10);
		Assert.assertEquals(dataSalt + e, data);
		// 算法不同
		e = DigestUtils.digest(input, "md5", DigestUtils.decodeHex(dataSalt), 10);
		Assert.assertNotEquals(dataSalt + e, data);
		// 盐不同
		e = DigestUtils.digest(input, "sha1", DigestUtils.generateSalt(8), 10);
		Assert.assertNotEquals(dataSalt + e, data);
		// 迭代次数不同
		e = DigestUtils.digest(input, "sha1", DigestUtils.decodeHex(dataSalt), 15);
		Assert.assertNotEquals(dataSalt + e, data);
		// 正常
		e = DigestUtils.digest(input, "sha1", DigestUtils.decodeHex(dataSalt), 10);
		Assert.assertEquals(dataSalt + e, data);

		// 长度不同的原文
		String str = "这是一段需要获取摘要的内容";
		logger.debug("{} --> {}", DigestUtils.digest(str, "sha1", salt, 10), str);
		str = "这是一段需要获取摘要的内容，非常非常长的文字哦，我找到你不相信，可又有什么办法！";
		logger.debug("{} --> {}", DigestUtils.digest(str, "sha1", salt, 10), str);

	}

	@Test
	public void testGenerateSalt() {
		for (int i = 0; i < 10; i++) {
			logger.debug("salt {} {}", (i + 1), DigestUtils.generateSalt(8));
		}
		for (int i = 0; i < 10; i++) {
			logger.debug("salt {} {}", (i + 1), DigestUtils.generateSalt(16));
		}
	}

}
