package com.github.hualuomoli.plugin.secure;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA
 * @author hualuomoli
 *
 */
public abstract class RSA extends EncryptionAdaptor implements Signature, Encryption {

	private static final Logger logger = LoggerFactory.getLogger(RSA.class);

	// 签名和验签算法
	private String algorithm = "SHA1withRSA";

	// 加解密算法
	private final String transformation = "RSA";

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	// 获取私钥
	protected abstract PrivateKey getPrivateKey();

	// 获取公钥
	protected abstract PublicKey getPublicKey();

	// 获取加密的key
	protected abstract Key getEncryptKey();

	// 获取解密的key
	protected abstract Key getDecryptKey();

	@Override
	public byte[] sign(byte[] origin) {
		try {
			java.security.Signature signature = java.security.Signature.getInstance(this.algorithm);
			signature.initSign(this.getPrivateKey());
			signature.update(origin);
			return signature.sign();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return null;
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
		try {
			java.security.Signature signature = java.security.Signature.getInstance(this.algorithm);
			signature.initVerify(this.getPublicKey());
			signature.update(origin);
			return signature.verify(signData);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return false;
	}

	@Override
	public boolean validString(String origin, String signData) {
		return this.valid(SecureUtils.dataToBytes(origin), SecureUtils.base64StringToBytes(signData));
	}

	@Override
	public byte[] encrypt(byte[] origin) {
		try {
			Cipher cipher = Cipher.getInstance(this.transformation);
			cipher.init(Cipher.ENCRYPT_MODE, this.getEncryptKey());
			return this.doEncrypt(cipher, origin);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
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
			Cipher cipher = Cipher.getInstance(this.transformation);
			cipher.init(Cipher.DECRYPT_MODE, this.getDecryptKey());
			return this.doDecrypt(cipher, cipherData);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
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
