package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.service.TreeService.TreeDealer;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;

// #BaseUserRole
public interface BaseUserRoleService {

	BaseUserRole get(BaseUserRole baseUserRole);
	
	BaseUserRole get(String id);
	
	
	int insert(BaseUserRole baseUserRole);
	
	int batchInsert(List<BaseUserRole> list);

	int update(BaseUserRole baseUserRole);
	
	

	int delete(BaseUserRole baseUserRole);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseUserRole> findList(BaseUserRole baseUserRole);
	
	List<BaseUserRole> findList(BaseUserRole baseUserRole, String... orderByStrArray);

	List<BaseUserRole> findList(BaseUserRole baseUserRole, Order... orders);

	List<BaseUserRole> findList(BaseUserRole baseUserRole, List<Order> orders);
	
	Integer getTotal(BaseUserRole baseUserRole);
	
	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseUserRole baseUserRole, Pagination pagination);
	
}
