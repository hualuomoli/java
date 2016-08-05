package com.github.hualuomoli.plugin.secure;

/**
 * 安全
 * @author hualuomoli
 *
 */
public interface Security {

	/**
	 * 加密
	 * @param origin 明文
	 * @return 密文
	 */
	byte[] encrypt(byte[] origin);

	/**
	 * 加密
	 * @param origin 明文
	 * @return 密文
	 */
	String encrypt(String origin);

	/**
	 * 解密
	 * @param cipherData 密文
	 * @return 明文
	 */
	byte[] decrypt(byte[] cipherData);

	/**
	 * 解密
	 * @param cipherData 密文
	 * @return 明文
	 */
	String decrypt(String cipherData);

	/**
	 * 签名
	 * @param origin 明文
	 * @return 签名数据
	 */
	byte[] sign(byte[] origin);

	/**
	 * 签名
	 * @param origin 明文
	 * @return 签名数据
	 */
	String sign(String origin);

	/**
	 * 验证
	 * @param origin 原文
	 * @param signData 签名数据
	 * @return 是否成功
	 */
	boolean valid(byte[] origin, byte[] signData);

	/**
	 * 验证
	 * @param origin 原文
	 * @param signData 签名数据
	 * @return 是否成功
	 */
	boolean valid(String origin, String signData);

}
