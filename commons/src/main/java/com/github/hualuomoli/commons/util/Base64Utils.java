package com.github.hualuomoli.commons.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.github.hualuomoli.commons.constant.Charset;

/**
 * Base64 编码和解码
 * @author hualuomoli
 *
 */
public class Base64Utils {

	// 编码
	public static byte[] encodeBase64(String data) {
		return encodeBase64(data.getBytes(Charset.UTF8));
	}

	// 编码
	public static String encodeBase64String(String data) {
		return encodeBase64String(data.getBytes(Charset.UTF8));
	}

	// 编码
	public static byte[] encodeBase64(byte[] binaryData) {
		return Base64.encodeBase64(binaryData);
	}

	// 编码
	public static String encodeBase64String(byte[] binaryData) {
		return Base64.encodeBase64String(binaryData);
	}

	// 解码
	public static String decodeBase64String(byte[] base64Data) {
		byte[] bytes = decodeBase64(base64Data);
		return StringUtils.newStringUtf8(bytes);
	}

	// 解码
	public static String decodeBase64String(String base64String) {
		byte[] bytes = decodeBase64(base64String);
		return StringUtils.newStringUtf8(bytes);
	}

	// 解码
	public static byte[] decodeBase64(byte[] base64Data) {
		return Base64.decodeBase64(base64Data);
	}

	// 解码
	public static byte[] decodeBase64(String base64String) {
		return Base64.decodeBase64(base64String);
	}

}
