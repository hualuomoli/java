package com.github.hualuomoli.demo.creator.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.creator.base.entity.BaseDemo;

// #BaseDemo
public interface BaseDemoService {

	BaseDemo get(BaseDemo baseDemo);
	
	BaseDemo get(String id);
	
	BaseDemo getByName(java.lang.String name);

	void insert(BaseDemo baseDemo);
	
	void batchInsert(List<BaseDemo> list);

	void update(BaseDemo baseDemo);
	
	void logicalDelete(BaseDemo baseDemo);

	void logicalDelete(String id);

	void delete(BaseDemo baseDemo);
	
	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

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
