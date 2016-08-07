package com.github.hualuomoli.plugin.secure;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.commons.util.DigestUtils;

/**
 * 摘要
 * @author hualuomoli
 *
 */
public abstract class Digest implements Security {

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
	public byte[] encrypt(byte[] origin) {
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
	public String encrypt(String origin) {
		byte[] salt = DigestUtils.generateSalt(saltNumber);
		String data = DigestUtils.digest(origin, algorithm, salt, iterations);

		return DigestUtils.encodeHex(salt) + data;
	}

	@Override
	public byte[] decrypt(byte[] cipherData) {
		throw new RuntimeException("can not support decrypt.");
	}

	@Override
	public String decrypt(String cipherData) {
		throw new RuntimeException("can not support decrypt.");
	}

	@Override
	public byte[] sign(byte[] origin) {
		return this.encrypt(origin);
	}

	@Override
	public String sign(String origin) {
		return this.encrypt(origin);
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
	public boolean valid(String origin, String signData) {
		try {
			String salt = signData.substring(0, saltNumber * 2);

			String data = DigestUtils.digest(origin, algorithm, DigestUtils.decodeHex(salt), iterations);
			return StringUtils.equals(salt + data, signData);
		} catch (Exception e) {
			return false;
		}
	}

}
