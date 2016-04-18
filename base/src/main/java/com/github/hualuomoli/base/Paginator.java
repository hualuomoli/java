package com.github.hualuomoli.base;

import com.github.hualuomoli.base.entity.Pagination;

/**
 * 分页接口，实体类如果需要分页，需要实现该接口
 * @author liubaoquan
 *
 */
public interface Paginator {

	/** 获取分页器 */
	Pagination getPagination();

}
