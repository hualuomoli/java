package com.github.hualuomoli.base.entity;

import java.util.List;

/**
 * 分页实体类
 * @author liubaoquan
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Pagination {

	private Integer pageNo = 1; // 当前页码，默认为1
	private Integer pageSize = 10; // 每页数据，默认为10

	private Integer count; // 总数量
	private List dataList; // 数据集合

	private String orderBy;// page query order by.such as order by user_code asc,user_name desc

	public Pagination() {
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public <T> List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	// other method
	/**
	 * 获取 Hibernate FirstResult
	 */
	public int getFirstResult() {
		int firstResult = (getPageNo() - 1) * getPageSize();
		// 不能大于最大数量
		if (firstResult >= getCount()) {
			firstResult = (getCount() - getPageSize() + 1);
		}
		// 不能小于零
		if (firstResult < 0) {
			firstResult = 0;
		}
		return firstResult;
	}

	/**
	 * 获取 Hibernate MaxResults
	 */
	public int getMaxResults() {
		return getPageSize();
	}

}
