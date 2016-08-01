package com.github.hualuomoli.plugin.secure;

import java.nio.charset.Charset;

import com.github.hualuomoli.commons.util.Base64Utils;

public abstract class SignatureAdaptor implements Signature {

	// 编码集
	protected Charset charset = com.github.hualuomoli.commons.constant.Charset.UTF8;

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	// 签名
	public String sign(String origin) {
		byte[] array = this.sign(origin.getBytes(this.charset));
		if (array == null) {
			return null;
		}
		return Base64Utils.encodeBase64String(array);
	}

	// 验签
	public boolean valid(String origin, String sign) {
		return this.valid(origin.getBytes(this.charset), Base64Utils.decodeBase64(sign));
	}

}
