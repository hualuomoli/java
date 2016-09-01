package com.github.hualuomoli.demo.creator.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorDemo;
import com.github.hualuomoli.demo.creator.entity.CreatorDemo;

// #BaseCreatorDemo
public interface BaseCreatorDemoService {

	BaseCreatorDemo get(CreatorDemo creatorDemo);
	
	BaseCreatorDemo get(String id);
	
	BaseCreatorDemo getUnique(
		java.lang.String name
	);
	
	int insert(CreatorDemo creatorDemo);
	
	<T extends CreatorDemo> int batchInsert(List<T> list);

	int update(CreatorDemo creatorDemo);
	
	 int logicalDelete(CreatorDemo creatorDemo);

	 int logicalDelete(String id);
	

	int delete(CreatorDemo creatorDemo);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo);
	
	List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, String... orderByStrArray);

	List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, Order... orders);

	List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, List<Order> orders);
	
	Integer getTotal(BaseCreatorDemo baseCreatorDemo);
	
	Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize);

	Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseCreatorDemo baseCreatorDemo, Pagination pagination);
	
}
