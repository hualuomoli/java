package com.github.hualuomoli.commons.constant;

/**
 * 编码集
 * @author hualuomoli
 *
 */
public enum Charset {

	UTF8("UTF-8"), // UTF-8
	GBK("gbk"), // GBK
	ISO88591("ISO-8895-1"), // ISO-8895-1
	;

	private String encoding;

	private Charset(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

}
