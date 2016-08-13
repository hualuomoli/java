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
import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.base.mapper.BaseRoleMapper;
import com.github.hualuomoli.extend.base.service.BaseRoleService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseRole
@Service(value = "com.github.hualuomoli.extend.base.service.BaseRoleServiceImpl")
@Transactional(readOnly = true)
public class BaseRoleServiceImpl implements BaseRoleService {

	@Autowired
	private BaseRoleMapper baseRoleMapper;
	
	@Override
	public BaseRole get(BaseRole baseRole) {
		return this.get(baseRole.getId());
	}
	
	@Override
	public BaseRole get(String id) {
		return baseRoleMapper.get(id);
	}
	
	@Override
	public BaseRole getUnique(
		java.lang.String roleCode
	) {
		BaseRole baseRole = new BaseRole();
		baseRole.setRoleCode(roleCode);
		List<BaseRole> list = this.findList(baseRole);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() != 1) {
			throw new MoreDataFoundException();
		}
		return list.get(0);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert BaseRole baseRole) {
		return baseRoleMapper.insert(baseRole);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseRole> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseRole> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseRoleMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseRole baseRole) {
		return baseRoleMapper.update(baseRole);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(BaseRole baseRole) {
		return this.delete(baseRole.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseRoleMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseRoleMapper.deleteByIds(ids);
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
	public List<BaseRole> findList(BaseRole baseRole) {
		return baseRoleMapper.findList(baseRole);
	}
	
	@Override
	public List<BaseRole> findList(BaseRole baseRole, String... orderByStrArray) {
		return this.findList(baseRole, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseRole> findList(BaseRole baseRole, Order... orders) {
		return this.findList(baseRole, new Pagination(orders));
	}

	@Override
	public List<BaseRole> findList(BaseRole baseRole, List<Order> orders) {
		return this.findList(baseRole, new Pagination(orders));
	}
	
	private List<BaseRole> findList(BaseRole baseRole, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseRole, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseRole baseRole) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseRole, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize) {
		return this.findPage(baseRole, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseRole, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseRole, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseRole baseRole, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseRole, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseRole baseRole, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseRole> list = baseRoleMapper.findList(baseRole);
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
