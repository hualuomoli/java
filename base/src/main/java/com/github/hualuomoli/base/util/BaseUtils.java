package com.github.hualuomoli.base.util;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.hualuomoli.base.entity.BaseEntity;
import com.github.hualuomoli.commons.constant.Status;
import com.google.common.collect.Lists;

/**
 * 工具
 * @author hualuomoli
 *
 */
public class BaseUtils {

	// 获取当前登录用户
	public static String getCurrentUser() {
		// TODO
		return "system";
	}

	// 获取当前时间
	public static Date getCurrentDate() {
		// TODO
		return new Date();
	}

	// 获取一个随机的ID
	public static String getRandomId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 批量插入前处理
	 * @param list 数据集合
	 */
	public static void preBatchInsert(List<? extends BaseEntity> list) {
		for (BaseEntity e : list) {

			String currentUser = BaseUtils.getCurrentUser();
			Date currentDate = BaseUtils.getCurrentDate();

			e.setCreateBy(currentUser);
			e.setCreateDate(currentDate);
			e.setUpdateBy(currentUser);
			e.setUpdateDate(currentDate);
			e.setStatus(Status.NOMAL.getValue());
			e.setVersion(1);
			e.setId(BaseUtils.getRandomId());
		}
	}

	public static int getBatchMaxCount() {
		// TODO
		return 1000;
	}

	/**
	 * 从集合中获取指定位置的数据
	 * @param list 集合数据
	 * @param listSplit 集合切分器
	 * @return 指定位置的数据
	 */
	public static <T> List<T> getList(List<T> list, ListSplit listSplit) {
		if (list == null || list.size() == 0) {
			return list;
		}
		List<T> retList = Lists.newArrayList();

		// 开始位置和截取长度
		int offset = listSplit.offset;
		int length = listSplit.length;

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
		listSplit.offset = end;
		listSplit.length = length;

		return retList;
	}

	// 集合切分器
	public static class ListSplit {

		private int offset;
		private int length;

		public ListSplit() {
		}

		public ListSplit(int length) {
			this.offset = 0;
			this.length = length;
		}

		public ListSplit(int offset, int length) {
			this.offset = offset;
			this.length = length;
		}

	}

}
