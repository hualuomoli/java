package com.github.hualuomoli.commons.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.constant.Charset;

/**
 * 消息摘要工具
 * @author hualuomoli
 *
 */
public class DigestUtils {

	private static final Logger logger = LoggerFactory.getLogger(DigestUtils.class);

	private static SecureRandom random = new SecureRandom();

	/**
	 * 十六进制编码,返回的数据长度为字节码数组的2倍
	 * @param input 字节码
	 * @return 十六进制字符串
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * 十六进制解码,返回的字节码数组长度为数据的1/2
	 * @param input 十六进制字符串
	 * @return 字节码
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e);
			}
			return null;
		}
	}

	/**
	 * 计算消息摘要
	 * @param input 数据
	 * @param algorithm 算法
	 * @param salt 加盐,用于散列
	 * @param iterations 迭代次数
	 * @return 消息摘要
	 */
	public static String digest(String input, String algorithm, byte[] salt, int iterations) {
		byte[] array = input.getBytes(Charset.UTF8);
		array = digest(array, algorithm, salt, iterations);
		return Base64Utils.encodeBase64String(array);
	}

	/**
	 * 计算消息摘要
	 * @param input 数据
	 * @param algorithm 算法
	 * @param salt 加盐,用于散列
	 * @param iterations 迭代次数
	 * @return 消息摘要
	 */
	public static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e);
			}
			return null;
		}
	}

	/**
	 * 获取指定位数的盐
	 * @param numBytes 位数
	 * @return 盐
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

}