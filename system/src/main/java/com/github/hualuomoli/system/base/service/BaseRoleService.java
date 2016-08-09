package com.github.hualuomoli.system.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.service.TreeService.TreeDealer;
import com.github.hualuomoli.system.base.entity.BaseRole;

// #BaseRole
public interface BaseRoleService {

	BaseRole get(BaseRole baseRole);
	
	BaseRole get(String id);
	
	BaseRole getUnique(
		java.lang.String roleCode
	);
	
	int insert(BaseRole baseRole);
	
	int batchInsert(List<BaseRole> list);

	int update(BaseRole baseRole);
	
	

	int delete(BaseRole baseRole);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseRole> findList(BaseRole baseRole);
	
	List<BaseRole> findList(BaseRole baseRole, String... orderByStrArray);

	List<BaseRole> findList(BaseRole baseRole, Order... orders);

	List<BaseRole> findList(BaseRole baseRole, List<Order> orders);
	
	Integer getTotal(BaseRole baseRole);
	
	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseRole baseRole, Pagination pagination);
	
}
