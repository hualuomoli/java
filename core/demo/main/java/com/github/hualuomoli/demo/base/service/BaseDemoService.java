package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.BaseDemo;

// #BaseDemo
public interface BaseDemoService {

	BaseDemo get(BaseDemo baseDemo);
	
	BaseDemo get(String id);
	
	BaseDemo getUnique(
		java.lang.String name
	);
	
	int insert(BaseDemo baseDemo);
	
	int batchInsert(List<BaseDemo> list);

	int update(BaseDemo baseDemo);
	
	 int logicalDelete(BaseDemo baseDemo);

	 int logicalDelete(String id);
	

	int delete(BaseDemo baseDemo);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseDemo> findList(BaseDemo baseDemo);
	
	List<BaseDemo> findList(BaseDemo baseDemo, String... orderByStrArray);

	List<BaseDemo> findList(BaseDemo baseDemo, Order... orders);

	List<BaseDemo> findList(BaseDemo baseDemo, List<Order> orders);
	
	Integer getTotal(BaseDemo baseDemo);
	
	Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize);

	Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseDemo baseDemo, Pagination pagination);
	
}
