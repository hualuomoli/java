package com.github.hualuomoli.plugin.secure;

/**
 * 签名延签
 * @author hualuomoli
 *
 */
public interface Signature {

	// 签名
	byte[] sign(byte[] origin);

	// 签名
	byte[] sign(String origin);

	// 签名
	String signString(byte[] origin);

	// 签名
	String signString(String origin);

	// 延签
	boolean valid(byte[] origin, byte[] signData);

	// 延签
	boolean validString(String origin, String signData);

}
