package com.github.hualuomoli.plugin.secure;

import java.nio.charset.Charset;

import com.github.hualuomoli.commons.util.Base64Utils;

/**
 * 加解密
 * @author hualuomoli
 *
 */
public abstract class EncryptionAdaptor implements Encryption {

	// 编码集
	protected Charset charset = com.github.hualuomoli.commons.constant.Charset.UTF8;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	// 加密
	public String encrypt(String origin) {
		byte[] array = this.encrypt(origin.getBytes(this.charset));
		if (array == null) {
			return null;
		}
		return Base64Utils.encodeBase64String(array);
	}

	// 解密
	public String decrypt(String cipherData) {
		byte[] array = this.decrypt(Base64Utils.decodeBase64(cipherData));
		if (array == null) {
			return null;
		}
		return new String(array, this.charset);
	}

}
