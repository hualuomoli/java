package com.github.hualuomoli.plugin.secure;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AES extends EncryptionAdaptor implements Encryption {

	private static final Logger logger = LoggerFactory.getLogger(AES.class);

	protected abstract byte[] getPassword();

	@Override
	public byte[] encrypt(byte[] origin) {

		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(this.getPassword()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			return this.doEncrypt(cipher, origin);
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e);
			}
		}
		return null;

	}

	@Override
	public byte[] encrypt(String origin) {
		return this.encrypt(SecureUtils.dataToBytes(origin));
	}

	@Override
	public String encryptString(byte[] origin) {
		return SecureUtils.bytesToBase64String(this.encrypt(origin));
	}

	@Override
	public String encryptString(String origin) {
		return this.encryptString(SecureUtils.dataToBytes(origin));
	}

	@Override
	public byte[] decrypt(byte[] cipherData) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(this.getPassword()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			return this.doDecrypt(cipher, cipherData);
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e);
			}
		}
		return null;
	}

	@Override
	public byte[] decrypt(String cipherData) {
		return this.decrypt(SecureUtils.base64StringToBytes(cipherData));
	}

	@Override
	public String decryptString(byte[] cipherData) {
		return SecureUtils.newString(this.decrypt(cipherData));
	}

	@Override
	public String decryptString(String cipherData) {
		return this.decryptString(SecureUtils.base64StringToBytes(cipherData));
	}

}
