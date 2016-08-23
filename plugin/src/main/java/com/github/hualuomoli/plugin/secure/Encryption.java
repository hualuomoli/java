package com.github.hualuomoli.plugin.secure;

/**
 * 加密解密
 * @author hualuomoli
 *
 */
public interface Encryption {

	// 加密
	byte[] encrypt(byte[] origin);

	// 加密
	byte[] encrypt(String origin);

	// 加密
	String encryptString(byte[] origin);

	// 加密
	String encryptString(String origin);

	// 解密
	byte[] decrypt(byte[] cipherData);

	// 解密
	byte[] decrypt(String cipherData);

	// 解密
	String decryptString(byte[] cipherData);

	// 解密
	String decryptString(String cipherData);

}
