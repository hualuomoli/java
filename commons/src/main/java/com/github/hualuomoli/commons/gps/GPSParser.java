package com.github.hualuomoli.commons.gps;

import com.github.hualuomoli.commons.gps.exception.GPSParserExcepton;

/**
 * GPS解析
 * @author hualuomoli
 *
 */
public interface GPSParser {

	/**
	 * 是否支持
	 * @param location 位置字符串
	 * @return 是否支持该字符串的解析
	 */
	boolean support(String location);

	/**
	 * 是否是合法数据
	 * @param location 位置字符串
	 * @return 数据是否合法
	 */
	boolean valid(String location);

	/**
	 * 解析GPS位置
	 * @param location 位置字符串
	 * @return 位置信息
	 */
	<T extends GPS> T parse(String location) throws GPSParserExcepton;

}
