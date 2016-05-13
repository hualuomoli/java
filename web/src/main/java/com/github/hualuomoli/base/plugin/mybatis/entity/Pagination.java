package com.github.hualuomoli.base.plugin.mybatis.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * Mybatis 分页
 * @author hualuomoli
 *
 */
public class Pagination {

	private Integer count; // 总数量
	private Integer pageNo; // 当前页码，默认为1
	private Integer pageSize; // 每页数据，默认为10
	private List<String> orderByStrArray; // 排序字符串
	private List<Order> orders; // 排序,如果排序字符串未设置,使用该集合排序

	public Pagination() {
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(Integer pageNo, Integer pageSize, String... orderByStrArray) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		if (orderByStrArray != null && orderByStrArray.length > 0) {
			this.orderByStrArray = Lists.newArrayList();
			for (String orderByStr : orderByStrArray) {
				if (StringUtils.isNotBlank(orderByStr)) {
					this.getOrderByStrArray().add(orderByStr);
				}
			}
		}
	}

	public Pagination(Integer pageNo, Integer pageSize, List<Order> orders) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orders = orders;
	}

	public Pagination(Integer pageNo, Integer pageSize, Order... orders) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		if (orders != null && orders.length > 0) {
			this.orders = Lists.newArrayList();
			for (Order order : orders) {
				if (order != null && StringUtils.isNotBlank(order.getColumn())) {
					this.orders.add(order);
				}
			}
		}
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public List<String> getOrderByStrArray() {
		return orderByStrArray;
	}

	public void setOrderByStrArray(List<String> orderByStrArray) {
		this.orderByStrArray = orderByStrArray;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
