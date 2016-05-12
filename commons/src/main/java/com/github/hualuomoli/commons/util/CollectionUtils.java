package com.github.hualuomoli.commons.util;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 集合工具类
 * @author hualuomoli
 *
 */
public class CollectionUtils {

	/**
	 * 从集合中获取指定位置的数据
	 * @param list 集合数据
	 * @param config 配置偏移和长度
	 * @return 指定位置的数据
	 */
	public static <T> List<T> fetchDatas(List<T> list, Config config) {
		if (list == null || list.size() == 0) {
			return list;
		}
		List<T> retList = Lists.newArrayList();

		// 开始位置和截取长度
		int offset = config.offset;
		int length = config.length;

		// 判断偏移量
		if (offset < 0) {
			offset = 0;
		}
		// 判断长度
		if (length <= 0) {
			length = 1;
		}
		// 获取数据的最后位置
		int end = offset + length;
		// 如果计算的位置比数据的长度大,最后的位置就是数据的长度
		if (end >= list.size()) {
			end = list.size();
		}
		for (int i = offset; i < end; i++) {
			retList.add(list.get(i));
		}

		// reset
		config.offset = end;
		config.length = length;

		return retList;
	}

	// 集合切分器
	public static class Config {

		private int offset;
		private int length;

		public Config() {
		}

		public Config(int length) {
			this.offset = 0;
			this.length = length;
		}

		public Config(int offset, int length) {
			this.offset = offset;
			this.length = length;
		}

	}

	/**
	 * 过滤集合
	 * @param list 集合数据
	 * @param filter 过滤器
	 * @return 过滤后的集合
	 */
	public static <T> List<T> filter(List<T> list, Filter<T> filter) {

		if (list == null || list.size() == 0 || filter == null) {
			return list;
		}

		List<T> retList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			if (filter.valid(t)) {
				retList.add(t);
			}
		}
		return retList;

	}

	// 过滤器
	public static interface Filter<T> {

		// 是否有效
		boolean valid(T t);

	}

}
