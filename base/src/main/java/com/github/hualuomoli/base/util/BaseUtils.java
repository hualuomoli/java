package com.github.hualuomoli.base.util;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.entity.BaseEntity;
import com.github.hualuomoli.commons.constant.Status;

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
		String currentUser = BaseUtils.getCurrentUser();
		Date currentDate = BaseUtils.getCurrentDate();

		for (BaseEntity e : list) {

			e.setCreateBy(currentUser);
			e.setCreateDate(currentDate);
			e.setUpdateBy(currentUser);
			e.setUpdateDate(currentDate);
			e.setStatus(e.getStatus() == null ? Status.NOMAL.getValue() : e.getStatus());
			e.setVersion(1);
			e.setId(StringUtils.isEmpty(e.getId()) ? BaseUtils.getRandomId() : e.getId());

		}
	}

	public static int getBatchMaxCount() {
		// TODO
		return 1000;
	}

}
