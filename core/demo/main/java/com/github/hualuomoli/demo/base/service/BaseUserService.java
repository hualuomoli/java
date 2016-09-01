package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.BaseUser;
import com.github.hualuomoli.demo.entity.User;

// #BaseUser
public interface BaseUserService {

	BaseUser get(User user);
	
	BaseUser get(String id);
	
	
	int insert(User user);
	
	<T extends User> int batchInsert(List<T> list);

	int update(User user);
	
	 int logicalDelete(User user);

	 int logicalDelete(String id);
	

	int delete(User user);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseUser> findList(BaseUser baseUser);
	
	List<BaseUser> findList(BaseUser baseUser, String... orderByStrArray);

	List<BaseUser> findList(BaseUser baseUser, Order... orders);

	List<BaseUser> findList(BaseUser baseUser, List<Order> orders);
	
	Integer getTotal(BaseUser baseUser);
	
	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseUser baseUser, Pagination pagination);
	
}
