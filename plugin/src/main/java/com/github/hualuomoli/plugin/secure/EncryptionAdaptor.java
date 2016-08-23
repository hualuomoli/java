package com.github.hualuomoli.plugin.secure;

import java.io.ByteArrayOutputStream;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA
 * @author hualuomoli
 *
 */
public abstract class EncryptionAdaptor implements Encryption {

	protected static final Logger logger = LoggerFactory.getLogger(EncryptionAdaptor.class);

	// 加密解密,输入的最长值
	private int inputLen = 1024;

	public void setInputLen(int inputLen) {
		this.inputLen = inputLen;
	}

	/** 执行加密 */
	protected byte[] doEncrypt(Cipher cipher, byte[] input) throws Exception {
		return this.doFinal(cipher, input, this.inputLen / 8 - 11);
	}

	/** 执行解密 */
	protected byte[] doDecrypt(Cipher cipher, byte[] input) throws Exception {
		return this.doFinal(cipher, input, this.inputLen / 8);
	}

	/** 执行加解密 */
	private byte[] doFinal(Cipher cipher, byte[] input, int maxLength) throws Exception {
		int length = input.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (length - offSet > 0) {
			if (length - offSet > maxLength) {
				cache = cipher.doFinal(input, offSet, maxLength);
			} else {
				cache = cipher.doFinal(input, offSet, length - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * maxLength;
		}
		byte[] output = out.toByteArray();
		out.close();

		return output;
	}

}
