package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.BaseUser;

// #BaseUser
public interface BaseUserService {

	BaseUser get(BaseUser baseUser);
	
	BaseUser get(String id);
	

	void insert(BaseUser baseUser);
	
	void batchInsert(List<BaseUser> list);

	void update(BaseUser baseUser);
	
	void logicalDelete(BaseUser baseUser);

	void logicalDelete(String id);

	void delete(BaseUser baseUser);
	
	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

	List<BaseUser> findList(BaseUser baseUser);
	
	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseUser baseUser, Pagination pagination);
	
}
