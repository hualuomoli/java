package com.github.hualuomoli.lang;

/**
 * 金额
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
public class Amount extends com.github.hualuomoli.lang.Number<Amount> {

	public Amount() {
		super();
	}

	public Amount(Long entire, Integer decimal) {
		super(entire, decimal);
	}

	public Amount(Long data) {
		super(data);
	}

	public Amount(String data) {
		super(data);
	}

	@Override
	protected int scal() {
		return 3;
	}

	@Override
	protected int scalData() {
		return 1000;
	}

}
