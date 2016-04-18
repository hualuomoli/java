package com.github.hualuomoli.ret.page.entity;

import java.io.Serializable;
import java.util.List;

/**
 * page message
 * @author hualuomoli
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -4195627771767010262L;

	private int total;
	private int pageSize;
	private int pageNumber;

	private List<T> list;

	public Page() {
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
