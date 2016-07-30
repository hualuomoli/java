package com.github.hualuomoli.commons.util;

import java.util.UUID;

/**
 * 随机数工具
 * @author hualuomoli
 *
 */
public class RandomUtils {

	public static void main(String[] args) {
		char s = 'a';
		String b = "";
		for (int i = 0; i < 32; i++) {
			char m = (char) (s + i);
			b += "\"" + m + "\",";
		}
		System.out.println(b);
	}

	private static final String[] numbers = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static final String[] array = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", //
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", //
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", //
			"_", //
	};

	/**
	 * 获取ID(32位)
	 * @return 32随机ID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("[-]", "");
	}

	/**
	 * @deprecated
	 */
	public static String getString() {
		return getUUID();
	}

	/**
	 * 获取随机字符串
	 */
	public static String getString(int length) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			buffer.append(array[getIndex(array.length)]);
		}
		return buffer.toString();
	}

	/**
	 * 获取数值
	 * @param length 长度
	 * @return 随机数值
	 */
	public static String getNumber(int length) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			buffer.append(numbers[getIndex(numbers.length)]);
		}
		return buffer.toString();
	}

	// 获取位置
	private static final int getIndex(int size) {
		return (int) (Math.random() * size);
	}

}
