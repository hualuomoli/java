package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.tree.service.TreeService.TreeDealer;

// #BaseRoleMenu
public interface BaseRoleMenuService {

	BaseRoleMenu get(BaseRoleMenu baseRoleMenu);
	
	BaseRoleMenu get(String id);
	
	
	int insert(BaseRoleMenu baseRoleMenu);
	
	int batchInsert(List<BaseRoleMenu> list);

	int update(BaseRoleMenu baseRoleMenu);
	
	

	int delete(BaseRoleMenu baseRoleMenu);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu);
	
	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, String... orderByStrArray);

	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, Order... orders);

	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, List<Order> orders);
	
	Integer getTotal(BaseRoleMenu baseRoleMenu);
	
	Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize);

	Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseRoleMenu baseRoleMenu, Pagination pagination);
	
}
