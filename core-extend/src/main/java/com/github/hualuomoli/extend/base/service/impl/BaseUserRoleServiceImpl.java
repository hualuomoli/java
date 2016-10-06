package com.github.hualuomoli.extend.base.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PreBatchInsert;
import com.github.hualuomoli.base.annotation.persistent.PreDelete;
import com.github.hualuomoli.base.annotation.persistent.PreInsert;
import com.github.hualuomoli.base.annotation.persistent.PreUpdate;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination.QueryType;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.entity.UserRole;
import com.github.hualuomoli.extend.base.mapper.BaseUserRoleMapper;
import com.github.hualuomoli.extend.base.service.BaseUserRoleService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseUserRole
@Service(value = "com.github.hualuomoli.extend.base.service.BaseUserRoleServiceImpl")
@Transactional(readOnly = true)
public class BaseUserRoleServiceImpl implements BaseUserRoleService {

	@Autowired
	private BaseUserRoleMapper baseUserRoleMapper;
	
	@Override
	public BaseUserRole get(UserRole userRole) {
		return this.get(userRole.getId());
	}
	
	@Override
	public BaseUserRole get(String id) {
		return baseUserRoleMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert UserRole userRole) {
		return baseUserRoleMapper.insert(userRole);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends UserRole> int batchInsert(@PreBatchInsert  List<T> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<T> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseUserRoleMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate UserRole userRole) {
		return baseUserRoleMapper.update(userRole);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(UserRole userRole) {
		return this.delete(userRole.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseUserRoleMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseUserRoleMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		if (ids == null || ids.size() == 0) {
			return 0;
		}
		return this.deleteByIds(ids.toArray(new String[]{}));
	}

	@Override
	public List<BaseUserRole> findList(BaseUserRole baseUserRole) {
		return baseUserRoleMapper.findList(baseUserRole);
	}
	
	@Override
	public List<BaseUserRole> findList(BaseUserRole baseUserRole, String... orderByStrArray) {
		return this.findList(baseUserRole, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseUserRole> findList(BaseUserRole baseUserRole, Order... orders) {
		return this.findList(baseUserRole, new Pagination(orders));
	}

	@Override
	public List<BaseUserRole> findList(BaseUserRole baseUserRole, List<Order> orders) {
		return this.findList(baseUserRole, new Pagination(orders));
	}
	
	private List<BaseUserRole> findList(BaseUserRole baseUserRole, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseUserRole, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseUserRole baseUserRole) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseUserRole, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize) {
		return this.findPage(baseUserRole, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseUserRole, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseUserRole, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseUserRole baseUserRole, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseUserRole, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseUserRole baseUserRole, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseUserRole> list = baseUserRoleMapper.findList(baseUserRole);
		// get local thread and remove
		pagination = PaginationInterceptor.popPagination();

		// set page
		Page page = new Page();
		page.setCount(pagination.getCount());
		page.setPageNo(pagination.getPageNo());
		page.setPageSize(pagination.getPageSize());
		page.setDataList(list);

		return page;
	}
	
}
