package com.github.hualuomoli.plugin.secure;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

/**
 * 安全工具
 * @author hualuomoli
 *
 */
public class SecureUtils {

	public static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Base64编码
	 * @param bytes 字节数组
	 * @return base64编码的字符串
	 */
	public static final String bytesToBase64String(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * base64编码字符串转字节数组
	 * @param base64String base64字符串
	 * @return 字节数组
	 */
	public static final byte[] base64StringToBytes(String base64String) {
		if (base64String == null) {
			return null;
		}
		return Base64.decodeBase64(base64String);
	}

	/**
	 * 数据转换为字节数组
	 * @param data 数据
	 * @return 字节数组
	 */
	public static final byte[] dataToBytes(String data) {
		return dataToBytes(data, CHARSET);
	}

	/**
	 * 数据转换为字节数组
	 * @param data 数据
	 * @param charset 数据编码
	 * @return 字节数组
	 */
	public static final byte[] dataToBytes(String data, Charset charset) {
		if (data == null) {
			return null;
		}
		return data.getBytes(charset);
	}

	/**
	 * 字节数组转换为字符串
	 * @param bytes 字节数组
	 * @return 字符串数据
	 */
	public static final String newString(byte[] bytes) {
		return new String(bytes, CHARSET);
	}

	/**
	 * 字节数组转换为字符串
	 * @param bytes 字节数组
	 * @param charset 编码集
	 * @return 字符串数据
	 */
	public static final String newString(byte[] bytes, Charset charset) {
		if (bytes == null) {
			return null;
		}
		return new String(bytes, charset);
	}

}
