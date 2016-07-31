package com.github.hualuomoli.base.entity;

import java.util.List;

/**
 * 分页组件
 * @author hualuomoli
 *
 */
@SuppressWarnings("rawtypes")
public class Page {

	private Integer pageNo; // 当前页码，默认为1
	private Integer pageSize; // 每页数据，默认为10
	private Integer count; // 总数量

	private List dataList;

	public Page() {
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

	@SuppressWarnings("unchecked")
	public <T> List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
