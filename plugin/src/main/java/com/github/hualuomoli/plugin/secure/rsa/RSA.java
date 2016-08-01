package com.github.hualuomoli.plugin.secure.rsa;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.util.Base64Utils;
import com.github.hualuomoli.plugin.secure.Encryption;
import com.github.hualuomoli.plugin.secure.Signature;

/**
 * RSA
 * @author hualuomoli
 *
 */
public abstract class RSA implements Signature, Encryption {

	private static final Logger logger = LoggerFactory.getLogger(RSA.class);

	// 编码集
	private Charset charset = com.github.hualuomoli.commons.constant.Charset.UTF8;
	// 签名和验签算法
	private String algorithm = "SHA1withRSA";

	// 转换使用的名称,该值为RSA
	private final String transformation = "RSA";
	// 加密解密,输入的最长值
	private int inputLen = 1024;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setInputLen(int inputLen) {
		this.inputLen = inputLen;
	}

	// 获取私钥
	protected abstract PrivateKey getPrivateKey();

	// 获取公钥
	protected abstract PublicKey getPublicKey();

	// 签名
	public String sign(String origin) {
		byte[] array = this.sign(origin.getBytes(this.charset));
		if (array == null) {
			return null;
		}
		return Base64Utils.encodeBase64String(array);
	}

	@Override
	public byte[] sign(byte[] origin) {
		try {
			java.security.Signature signature = java.security.Signature.getInstance(this.algorithm);
			signature.initSign(this.getPrivateKey());
			signature.update(origin);
			return signature.sign();
		} catch (NoSuchAlgorithmException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (InvalidKeyException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (SignatureException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return null;
	}

	// 验签
	public boolean valid(String origin, String sign) {
		return this.valid(origin.getBytes(this.charset), Base64Utils.decodeBase64(sign));
	}

	@Override
	public boolean valid(byte[] origin, byte[] sign) {
		try {
			java.security.Signature signature = java.security.Signature.getInstance(this.algorithm);
			signature.initVerify(this.getPublicKey());
			signature.update(origin);
			return signature.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (InvalidKeyException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (SignatureException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return false;
	}

	// 获取加密的key
	protected abstract Key getEncryptKey();

	// 获取解密的key
	protected abstract Key getDecryptKey();

	// 加密
	public String encrypt(String origin) {
		byte[] array = this.encrypt(origin.getBytes(this.charset));
		if (array == null) {
			return null;
		}
		return Base64Utils.encodeBase64String(array);
	}

	@Override
	public byte[] encrypt(byte[] origin) {
		try {
			Cipher cipher = Cipher.getInstance(this.transformation);
			cipher.init(Cipher.ENCRYPT_MODE, this.getEncryptKey());
			return this.doFinal(cipher, origin, this.inputLen / 8 - 11);
		} catch (NoSuchAlgorithmException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (NoSuchPaddingException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (InvalidKeyException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return null;
	}

	// 解密
	public String decrypt(String cipherData) {
		byte[] array = this.decrypt(Base64Utils.decodeBase64(cipherData));
		if (array == null) {
			return null;
		}
		return new String(array, this.charset);
	}

	@Override
	public byte[] decrypt(byte[] cipherData) {
		try {
			Cipher cipher = Cipher.getInstance(this.transformation);
			cipher.init(Cipher.DECRYPT_MODE, this.getDecryptKey());
			return this.doFinal(cipher, cipherData, this.inputLen / 8);
		} catch (NoSuchAlgorithmException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (NoSuchPaddingException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (InvalidKeyException e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("{}", e);
			}
		}
		return null;
	}

	/** 执行加解密 */
	private byte[] doFinal(Cipher cipher, byte[] input, int maxLength) throws Exception {
		int length = input.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (length - offSet > 0) {
			if (length - offSet > maxLength) {
				cache = cipher.doFinal(input, offSet, maxLength);
			} else {
				cache = cipher.doFinal(input, offSet, length - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * maxLength;
		}
		byte[] output = out.toByteArray();
		out.close();

		return output;
	}

}
