package com.github.hualuomoli.plugin.secure;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;
import com.github.hualuomoli.commons.util.Base64Utils;
import com.github.hualuomoli.commons.util.ResourceUtils;

public class RSATest {

	private static final Logger logger = LoggerFactory.getLogger(RSA.class);

	private static RSA rsa = null;
	private static String password = null;
	private static String origin = null;
	private static String content = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchAlgorithmException, IOException {
		rsa = new RSAAdaptor();
		password = "shandong2016";
		origin = "这是一段没有用的代码,你现在正在看，不觉得浪费你的时间吗？";
		content = ResourceUtils.getResourceContent("content.txt", Charset.UTF8);
	}

	@Test
	public void testSignPassword() {
		String sign = rsa.signString(password);
		logger.debug("password {}", sign);
		boolean success = rsa.validString(password, sign);
		Assert.assertTrue(success);
	}

	@Test
	public void testSign() {
		String sign = rsa.signString(origin);
		logger.debug("sign {}", sign);
		boolean success = rsa.validString(origin, sign);
		Assert.assertTrue(success);
	}

	@Test
	public void testSignLongContent() {
		String sign = rsa.signString(content);
		logger.debug("sign {}", sign);
		boolean success = rsa.validString(content, sign);
		Assert.assertTrue(success);
	}

	@Test
	public void testEncrypt() {
		String cipherData = rsa.encryptString(origin);
		logger.debug("cipher {}", cipherData);
		String check = rsa.decryptString(cipherData);
		Assert.assertEquals(origin, check);
	}

	@Test
	public void testEncryptLongContent() {
		String cipherData = rsa.encryptString(content);
		logger.debug("cipher {}", cipherData);
		String check = rsa.decryptString(cipherData);
		Assert.assertEquals(content, check);
	}

	private static final class RSAAdaptor extends RSA {

		private RSAPublicKey publicKey;
		private RSAPrivateKey privateKey;

		public RSAAdaptor() throws NoSuchAlgorithmException {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();

			this.publicKey = (RSAPublicKey) keyPair.getPublic();
			this.privateKey = (RSAPrivateKey) keyPair.getPrivate();

			this.publicKey.getEncoded();

			logger.info("public key:  " + Base64Utils.encodeBase64String(this.publicKey.getEncoded()));
			logger.info("private key:  " + Base64Utils.encodeBase64String(this.privateKey.getEncoded()));

		}

		@Override
		protected PublicKey getPublicKey() {
			return publicKey;
		}

		@Override
		protected PrivateKey getPrivateKey() {
			return privateKey;
		}

		@Override
		protected Key getEncryptKey() {
			return publicKey;
		}

		@Override
		protected Key getDecryptKey() {
			return privateKey;
		}

	}

}
