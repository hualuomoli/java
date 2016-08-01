package com.github.hualuomoli.plugin.secure;

/**
 * 数字签名
 * @author hualuomoli
 *
 */
public interface Signature extends Security {

	/**
	 * 签名(使用私钥)
	 * @param origin 明文
	 * @return 签名
	 */
	byte[] sign(byte[] origin);

	/**
	 * 验证签名有效性(使用公钥)
	 * @param origin 明文
	 * @param sign 签名
	 * @return 签名有效性
	 */
	boolean valid(byte[] origin, byte[] sign);

}
