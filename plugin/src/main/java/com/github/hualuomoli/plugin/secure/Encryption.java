package com.github.hualuomoli.plugin.secure;

/**
 * 加解密
 * @author hualuomoli
 *
 */
public interface Encryption extends Security {

	/**
	 * 加密
	 * @param origin 明文
	 * @return 密文
	 */
	byte[] encrypt(byte[] origin);

	/**
	 * 解密
	 * @param cipherData 密文
	 * @return 明文
	 */
	byte[] decrypt(byte[] cipherData);

}
