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

	private QueryType queryType = QueryType.ALL; // 查询类型

	public Pagination() {
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(QueryType queryType) {
		this.queryType = queryType;
	}

	public Pagination(String... orderByStrArray) {
		this(null, null, orderByStrArray);
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

	public Pagination(List<Order> orders) {
		this(null, null, orders);
	}

	public Pagination(Integer pageNo, Integer pageSize, List<Order> orders) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orders = orders;
	}

	public Pagination(Order... orders) {
		this(null, null, orders);
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

	public void setOrderByStrArray(String... orderByStrArray) {
		if (orderByStrArray == null || orderByStrArray.length == 0) {
			return;
		}
		this.orderByStrArray = Lists.newArrayList();
		for (String orderByStr : orderByStrArray) {
			this.orderByStrArray.add(orderByStr);
		}
	}

	public void setOrders(Order... orders) {
		if (orders == null || orders.length == 0) {
			return;
		}
		this.orders = Lists.newArrayList();
		for (Order order : orders) {
			this.orders.add(order);
		}
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	// 查询类型
	public static enum QueryType {

		ONLY_COUNT(), // 只查询数量
		ONLY_DATA(), // 只查询数据,包含排序
		ALL(), // 查询数量和数据
		;

		private QueryType() {
		}

	}

}
