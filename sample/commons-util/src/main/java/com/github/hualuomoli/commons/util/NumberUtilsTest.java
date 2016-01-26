package com.github.hualuomoli.commons.util;

import org.junit.Test;

public class NumberUtilsTest {

	@Test
	public void testParseDouble() {
		System.out.println(NumberUtils.parseDouble(0.00123, 2));
		System.out.println(NumberUtils.parseDouble(0.0000, 2));
		System.out.println(NumberUtils.parseDouble(5.1699999999, 2));
		System.out.println(NumberUtils.parseDouble(5.1699999999, 3));
		System.out.println(NumberUtils.parseDouble(5.1699999999, 0));
		System.out.println(NumberUtils.parseDouble(5.1699999999, -1));
	}

}
