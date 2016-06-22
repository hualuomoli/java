package com.github.hualuomoli.plugin.mybatis.entity;

/**
 * Mybatis 分页
 * @author hualuomoli
 *
 */
public class Pagination {

	private Integer pageNo; // 当前页码，默认为1
	private Integer pageSize; // 每页数据，默认为10
	private Integer count; // 总数量

	public Pagination() {
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

}
