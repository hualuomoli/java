package com.github.hualuomoli.commons.util;

import java.util.UUID;

/**
 * 随机数工具
 * @author hualuomoli
 *
 */
public class RandomUtils {

	/**
	 * 获取ID(32位)
	 * @return 32随机ID
	 */
	public static String getString() {
		return UUID.randomUUID().toString().replaceAll("[-]", "");
	}

}
