package com.github.hualuomoli.lang;

/**
 * 价格
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
public class Price extends com.github.hualuomoli.lang.Number<Price> {

	public Price() {
		super();
	}

	public Price(Long entire, Integer decimal) {
		super(entire, decimal);
	}

	public Price(Long data) {
		super(data);
	}

	public Price(String data) {
		super(data);
	}

}
