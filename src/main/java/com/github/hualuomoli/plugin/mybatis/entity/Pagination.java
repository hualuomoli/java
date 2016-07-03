package com.github.hualuomoli.plugin.mybatis.entity;

/**
 * Mybatis 分页
 * @author hualuomoli
 *
 */
public class Pagination {

	private Integer count; // 总数量
	private Integer pageNo; // 当前页码，默认为1
	private Integer pageSize; // 每页数据，默认为10
	private String orderByStr; // 排序字符串

	public Pagination() {
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(Integer pageNo, Integer pageSize, String orderByStr) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orderByStr = orderByStr;
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

	public String getOrderByStr() {
		return orderByStr;
	}

	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}

}
