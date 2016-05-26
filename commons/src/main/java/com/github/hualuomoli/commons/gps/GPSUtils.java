package com.github.hualuomoli.commons.gps;

import java.util.List;

import com.github.hualuomoli.commons.gps.exception.GPSParserExcepton;
import com.github.hualuomoli.commons.gps.rmc.GPRMCParser;
import com.google.common.collect.Lists;

/**
 * GPS工具类
 * @author hualuomoli
 *
 */
public class GPSUtils {

	private static List<GPSParser> defaultParserList = getDefault();

	/**
	 * 解析
	 * @param location 位置
	 * @return 解析结果
	 */
	public static <T extends GPS> T parse(String location) throws GPSParserExcepton {
		return parse(location, defaultParserList);
	}

	/**
	 * 解析
	 * @param location 位置
	 * @param parserList 解析器
	 * @return 解析结果
	 */
	public static <T extends GPS> T parse(String location, List<GPSParser> parserList) throws GPSParserExcepton {
		if (parserList == null) {
			parserList = defaultParserList;
		}
		for (int i = 0; i < parserList.size(); i++) {
			GPSParser parser = parserList.get(i);

			// 支持
			if (parser.support(location)) {
				// 非有效数据
				if (!parser.valid(location)) {
					throw GPSParserExcepton.INVALID;
				}
				return parser.parse(location);
			}
		}
		return null;
	}

	private static List<GPSParser> getDefault() {
		List<GPSParser> parserList = Lists.newArrayList();

		parserList.add(new GPRMCParser());

		return parserList;
	}

}
