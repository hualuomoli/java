package com.github.hualuomoli.plugin.secure;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;
import com.github.hualuomoli.commons.util.ResourceUtils;

public class AESTest {

	private static final Logger logger = LoggerFactory.getLogger(AES.class);

	private static AES aes = null;
	private static String origin = null;
	private static String content = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchAlgorithmException, IOException {
		aes = new AESAdaptor();
		origin = "这是一段没有用的代码,你现在正在看，不觉得浪费你的时间吗？";
		content = ResourceUtils.getResourceContent("content.txt", Charset.UTF8);
	}

	@Test
	public void testEncrypt() {
		String cipherData = aes.encryptString(origin);
		logger.debug("cipher {}", cipherData);
		String check = aes.decryptString(cipherData);
		Assert.assertEquals(origin, check);
	}

	@Test
	public void testEncryptLongContent() {
		String cipherData = aes.encryptString(content);
		logger.debug("cipher {}", cipherData);
		String check = aes.decryptString(cipherData);
		Assert.assertEquals(content, check);
	}

	private static final class AESAdaptor extends AES {

		private byte[] password = "花落莫离".getBytes(SecureUtils.CHARSET);

		@Override
		protected byte[] getPassword() {
			return password;
		}

	}

}
