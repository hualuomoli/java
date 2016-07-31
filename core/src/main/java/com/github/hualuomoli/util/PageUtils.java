package com.github.hualuomoli.util;

import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.CollectionUtils;

/**
 * Page工具
 * @author hualuomoli
 *
 */
public class PageUtils {

	/**
	 * 转换数据
	 * @param page page数据
	 * @param parser 解析器
	 */
	public static <S, D> void transfer(Page page, CollectionUtils.Parser<S, D> parser) {
		if (page == null || page.getDataList() == null || page.getDataList().isEmpty() || parser == null) {
			return;
		}
		List<S> srcList = page.getDataList();
		List<D> destList = CollectionUtils.transfer(srcList, parser);
		page.setDataList(destList);
	}

}
