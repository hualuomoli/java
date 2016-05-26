package com.github.hualuomoli.commons.gps;

/**
 * @author hualuomoli
 *
 */
public abstract class GPSParserAbstract implements GPSParser {

	private String[] array; // 数据
	private int index = -1; // 数据位置

	public void setArray(String[] array) {
		this.array = array;
	}

	// 当前数据
	public String current() {
		return array[index];
	}

	// 下一个数据
	public String next() {
		return array[++index];
	}

}
