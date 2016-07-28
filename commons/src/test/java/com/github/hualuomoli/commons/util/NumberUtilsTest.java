package com.github.hualuomoli.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {

	@Test
	public void testTrimZero() {

		String n1 = "00000000000000";
		String n2 = "000000.0000";
		String n3 = "0.00000000";
		String n4 = "000000000.0";
		String n5 = "000120.000";
		String n6 = "000120.100";
		String n7 = "0000.010";
		String n8 = "000.2020";

		Assert.assertEquals("0", NumberUtils.trimZero(n1));
		Assert.assertEquals("0", NumberUtils.trimZero(n2));
		Assert.assertEquals("0", NumberUtils.trimZero(n3));
		Assert.assertEquals("0", NumberUtils.trimZero(n4));
		Assert.assertEquals("120", NumberUtils.trimZero(n5));
		Assert.assertEquals("120.1", NumberUtils.trimZero(n6));
		Assert.assertEquals("0.01", NumberUtils.trimZero(n7));
		Assert.assertEquals("0.202", NumberUtils.trimZero(n8));

	}

	@Test
	public void testTrimLeftZero() {
		String n1 = "00000000000000";
		String n2 = "000000.0000";
		String n3 = "0.00000000";
		String n4 = "000000000.0";
		String n5 = "0000.123";
		String n6 = "0001.0215";
		String n7 = "1230.0123";
		String n8 = "0123.021";

		Assert.assertEquals("0", NumberUtils.trimLeftZero(n1));
		Assert.assertEquals("0.0000", NumberUtils.trimLeftZero(n2));
		Assert.assertEquals("0.00000000", NumberUtils.trimLeftZero(n3));
		Assert.assertEquals("0.0", NumberUtils.trimLeftZero(n4));
		Assert.assertEquals("0.123", NumberUtils.trimLeftZero(n5));
		Assert.assertEquals("1.0215", NumberUtils.trimLeftZero(n6));
		Assert.assertEquals("1230.0123", NumberUtils.trimLeftZero(n7));
		Assert.assertEquals("123.021", NumberUtils.trimLeftZero(n8));
	}

	@Test
	public void testTrimRightZero() {
		String n1 = "00000000000000";
		String n2 = "000000.0000";
		String n3 = "0.00000000";
		String n4 = "000000000.0";
		String n5 = "120.000";
		String n6 = "120.100";
		String n7 = "120.010";
		String n8 = "123.2020";

		Assert.assertEquals("0", NumberUtils.trimRightZero(n1));
		Assert.assertEquals("000000", NumberUtils.trimRightZero(n2));
		Assert.assertEquals("0", NumberUtils.trimRightZero(n3));
		Assert.assertEquals("000000000", NumberUtils.trimRightZero(n4));
		Assert.assertEquals("120", NumberUtils.trimRightZero(n5));
		Assert.assertEquals("120.1", NumberUtils.trimRightZero(n6));
		Assert.assertEquals("120.01", NumberUtils.trimRightZero(n7));
		Assert.assertEquals("123.202", NumberUtils.trimRightZero(n8));

	}

	@Test
	public void testShiftLeft() {
		int length = 3;
		String n1 = "1234";
		String n2 = "1230";
		String n3 = "123";
		String n4 = "230";
		String n5 = "34";
		String n6 = "30";
		String n7 = "1234.0123";
		String n8 = "234.0123";
		String n9 = "3.0123";

		Assert.assertEquals("1.234", NumberUtils.shiftLeft(n1, length));
		Assert.assertEquals("1.23", NumberUtils.shiftLeft(n2, length));
		Assert.assertEquals("0.123", NumberUtils.shiftLeft(n3, length));
		Assert.assertEquals("0.23", NumberUtils.shiftLeft(n4, length));
		Assert.assertEquals("0.034", NumberUtils.shiftLeft(n5, length));
		Assert.assertEquals("0.03", NumberUtils.shiftLeft(n6, length));
		Assert.assertEquals("1.2340123", NumberUtils.shiftLeft(n7, length));
		Assert.assertEquals("0.2340123", NumberUtils.shiftLeft(n8, length));
		Assert.assertEquals("0.0030123", NumberUtils.shiftLeft(n9, length));

	}

	@Test
	public void testShiftRight() {
		int length = 3;
		String n1 = "1";
		String n2 = "0.001";
		String n3 = "0.01";
		String n4 = "0.1";

		Assert.assertEquals("1000", NumberUtils.shiftRight(n1, length));
		Assert.assertEquals("1", NumberUtils.shiftRight(n2, length));
		Assert.assertEquals("10", NumberUtils.shiftRight(n3, length));
		Assert.assertEquals("100", NumberUtils.shiftRight(n4, length));

	}

}
