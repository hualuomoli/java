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
import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.entity.RoleMenu;
import com.github.hualuomoli.extend.base.mapper.BaseRoleMenuMapper;
import com.github.hualuomoli.extend.base.service.BaseRoleMenuService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseRoleMenu
@Service(value = "com.github.hualuomoli.extend.base.service.BaseRoleMenuServiceImpl")
@Transactional(readOnly = true)
public class BaseRoleMenuServiceImpl implements BaseRoleMenuService {

	@Autowired
	private BaseRoleMenuMapper baseRoleMenuMapper;
	
	@Override
	public BaseRoleMenu get(RoleMenu roleMenu) {
		return this.get(roleMenu.getId());
	}
	
	@Override
	public BaseRoleMenu get(String id) {
		return baseRoleMenuMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert RoleMenu roleMenu) {
		return baseRoleMenuMapper.insert(roleMenu);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends RoleMenu> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += baseRoleMenuMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate RoleMenu roleMenu) {
		return baseRoleMenuMapper.update(roleMenu);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(RoleMenu roleMenu) {
		return this.delete(roleMenu.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseRoleMenuMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseRoleMenuMapper.deleteByIds(ids);
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
	public List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu) {
		return baseRoleMenuMapper.findList(baseRoleMenu);
	}
	
	@Override
	public List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, String... orderByStrArray) {
		return this.findList(baseRoleMenu, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, Order... orders) {
		return this.findList(baseRoleMenu, new Pagination(orders));
	}

	@Override
	public List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, List<Order> orders) {
		return this.findList(baseRoleMenu, new Pagination(orders));
	}
	
	private List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseRoleMenu, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseRoleMenu baseRoleMenu) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseRoleMenu, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize) {
		return this.findPage(baseRoleMenu, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseRoleMenu, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseRoleMenu, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseRoleMenu baseRoleMenu, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseRoleMenu, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseRoleMenu baseRoleMenu, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseRoleMenu> list = baseRoleMenuMapper.findList(baseRoleMenu);
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
