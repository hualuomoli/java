package com.github.hualuomoli.commons.util;

import java.util.UUID;

/**
 * 随机数工具
 * @author hualuomoli
 *
 */
public class RandomUtils {

	private static final String[] numbers = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	/**
	 * 获取ID(32位)
	 * @return 32随机ID
	 */
	public static String getString() {
		return UUID.randomUUID().toString().replaceAll("[-]", "");
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
