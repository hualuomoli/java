package com.github.hualuomoli.plugin.secure;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hualuomoli.commons.util.RandomUtils;

public class DigestTest {

	private static final String str = "这是一段文字，包含中文信息哦" + RandomUtils.getString(20);
	private static final Charset charset = com.github.hualuomoli.commons.constant.Charset.UTF8;

	private static Digest digest;

	@BeforeClass
	public static void beforeClass() {
		digest = new Digest() {
		};
		digest.setAlgorithm("sha1");
		digest.setIterations(1024);
		digest.setSaltNumber(8);
	}

	@Test
	public void testEncryptByteArray() {
		byte[] data = digest.encrypt(str.getBytes(charset));
		boolean success = digest.valid(str.getBytes(charset), data);
		Assert.assertTrue("valid", success);
	}

	@Test
	public void testEncryptString() {
		String data = digest.encrypt(str);
		boolean success = digest.valid(str, data);
		Assert.assertTrue("valid", success);
	}

	@Test
	public void testSignByteArray() {
		byte[] data = digest.encrypt(str.getBytes(charset));
		boolean success = digest.valid(str.getBytes(charset), data);
		Assert.assertTrue("valid", success);
	}

	@Test
	public void testSignString() {
		String data = digest.encrypt(str);
		boolean success = digest.valid(str, data);
		Assert.assertTrue("valid", success);
	}

	@Test
	public void testValidByteArrayByteArray() {
		byte[] data = digest.encrypt(str.getBytes(charset));
		boolean success = digest.valid(str.getBytes(charset), data);
		Assert.assertTrue("valid", success);
	}

	@Test
	public void testValidStringString() {
		String data = digest.encrypt(str);
		boolean success = digest.valid(str, data);
		Assert.assertTrue("valid", success);
	}

}
