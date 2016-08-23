package com.github.hualuomoli.plugin.secure;

import com.github.hualuomoli.commons.util.DigestUtils;

/**
 * 摘要
 * @author hualuomoli
 *
 */
public abstract class Digest implements Signature {

	private String algorithm; // 算法
	private Integer saltNumber; // 盐的长度
	private Integer iterations; // 迭代次数

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setSaltNumber(Integer saltNumber) {
		this.saltNumber = saltNumber;
	}

	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}

	@Override
	public byte[] sign(byte[] origin) {
		byte[] salt = DigestUtils.generateSalt(saltNumber);
		byte[] data = DigestUtils.digest(origin, algorithm, salt, iterations);

		byte[] ret = new byte[salt.length + data.length];
		for (int i = 0; i < salt.length; i++) {
			ret[i] = salt[i];
		}
		for (int j = 0; j < data.length; j++) {
			ret[j + salt.length] = data[j];
		}
		return ret;
	}

	@Override
	public byte[] sign(String origin) {
		return this.sign(SecureUtils.dataToBytes(origin));
	}

	@Override
	public String signString(byte[] origin) {
		return SecureUtils.bytesToBase64String(this.sign(origin));
	}

	@Override
	public String signString(String origin) {
		return this.signString(SecureUtils.dataToBytes(origin));
	}

	@Override
	public boolean valid(byte[] origin, byte[] signData) {
		byte[] salt = new byte[saltNumber];
		for (int i = 0; i < salt.length; i++) {
			salt[i] = signData[i];
		}

		// get data from salt
		byte[] data = DigestUtils.digest(origin, algorithm, salt, iterations);

		// check
		if (salt.length + data.length != signData.length) {
			return false;
		}

		for (int i = 0; i < data.length; i++) {
			if (data[i] != signData[salt.length + i]) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean validString(String origin, String signData) {
		return this.valid(SecureUtils.dataToBytes(origin), SecureUtils.base64StringToBytes(signData));
	}

}
