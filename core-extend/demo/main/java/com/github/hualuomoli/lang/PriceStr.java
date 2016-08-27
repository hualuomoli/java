package com.github.hualuomoli.lang;

/**
 * 价格
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
public class PriceStr extends com.github.hualuomoli.lang.Number<PriceStr> {

	public PriceStr() {
		super();
	}

	public PriceStr(Long entire, Integer decimal) {
		super(entire, decimal);
	}

	public PriceStr(Long data) {
		super(data);
	}

	public PriceStr(String data) {
		super(data);
	}

}
