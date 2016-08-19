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
import com.github.hualuomoli.extend.base.entity.BaseUser;
import com.github.hualuomoli.extend.base.mapper.BaseUserMapper;
import com.github.hualuomoli.extend.base.service.BaseUserService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseUser
@Service(value = "com.github.hualuomoli.extend.base.service.BaseUserServiceImpl")
@Transactional(readOnly = true)
public class BaseUserServiceImpl implements BaseUserService {

	@Autowired
	private BaseUserMapper baseUserMapper;
	
	@Override
	public BaseUser get(BaseUser baseUser) {
		return this.get(baseUser.getId());
	}
	
	@Override
	public BaseUser get(String id) {
		return baseUserMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert BaseUser baseUser) {
		return baseUserMapper.insert(baseUser);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseUser> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseUser> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseUserMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseUser baseUser) {
		return baseUserMapper.update(baseUser);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(BaseUser baseUser) {
		return this.delete(baseUser.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseUserMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseUserMapper.deleteByIds(ids);
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
	public List<BaseUser> findList(BaseUser baseUser) {
		return baseUserMapper.findList(baseUser);
	}
	
	@Override
	public List<BaseUser> findList(BaseUser baseUser, String... orderByStrArray) {
		return this.findList(baseUser, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseUser> findList(BaseUser baseUser, Order... orders) {
		return this.findList(baseUser, new Pagination(orders));
	}

	@Override
	public List<BaseUser> findList(BaseUser baseUser, List<Order> orders) {
		return this.findList(baseUser, new Pagination(orders));
	}
	
	private List<BaseUser> findList(BaseUser baseUser, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseUser, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseUser baseUser) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseUser, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseUser baseUser, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseUser> list = baseUserMapper.findList(baseUser);
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
