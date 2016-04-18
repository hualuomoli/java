package com.github.hualuomoli.commons.util;

import java.text.DecimalFormat;

/**
 * parse number to string
 * @author hualuomoli
 *
 */
public class NumberUtils {

	public static final DecimalFormat df = new DecimalFormat("#0");
	public static final DecimalFormat df1 = new DecimalFormat("#0.0");
	public static final DecimalFormat df2 = new DecimalFormat("#0.00");
	public static final DecimalFormat df3 = new DecimalFormat("#0.000");

	public static final String ZERO = "0";

	// parse float to string
	public static String parseFloat(Float f, int scale) {
		if (f == 0) {
			return ZERO;
		}
		if (scale < 0) {
			throw new RuntimeException("scale must greater zero.");
		}
		switch (scale) {
		case 0:
			return df.format(f);
		case 1:
			return df1.format(f);
		case 2:
			return df2.format(f);
		case 3:
			return df3.format(f);
		default: {
			String s = "";
			for (int i = 0; i < scale; i++) {
				s += "0";
			}
			DecimalFormat df = new DecimalFormat("#0." + s);
			return df.format(f);
		}
		}
	}

	// parse double to string
	public static String parseDouble(Double d, int scale) {
		if (d == 0) {
			return ZERO;
		}
		if (scale < 0) {
			throw new RuntimeException("scale must greater zero.");
		}
		switch (scale) {
		case 0:
			return df.format(d);
		case 1:
			return df1.format(d);
		case 2:
			return df2.format(d);
		case 3:
			return df3.format(d);
		default: {
			String s = "";
			for (int i = 0; i < scale; i++) {
				s += "0";
			}
			DecimalFormat df = new DecimalFormat("#0." + s);
			return df.format(d);
		}
		}
	}

}
