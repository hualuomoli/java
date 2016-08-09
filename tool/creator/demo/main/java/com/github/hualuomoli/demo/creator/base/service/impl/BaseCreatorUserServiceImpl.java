package com.github.hualuomoli.demo.creator.base.service.impl;

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
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorUser;
import com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorUserMapper;
import com.github.hualuomoli.demo.creator.base.service.BaseCreatorUserService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseCreatorUser
@Service(value = "com.github.hualuomoli.demo.creator.base.service.BaseCreatorUserServiceImpl")
@Transactional(readOnly = true)
public class BaseCreatorUserServiceImpl implements BaseCreatorUserService {

	@Autowired
	private BaseCreatorUserMapper baseCreatorUserMapper;
	
	@Override
	public BaseCreatorUser get(BaseCreatorUser baseCreatorUser) {
		return this.get(baseCreatorUser.getId());
	}
	
	@Override
	public BaseCreatorUser get(String id) {
		return baseCreatorUserMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert BaseCreatorUser baseCreatorUser) {
		return baseCreatorUserMapper.insert(baseCreatorUser);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseCreatorUser> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseCreatorUser> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseCreatorUserMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseCreatorUser baseCreatorUser) {
		return baseCreatorUserMapper.update(baseCreatorUser);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete BaseCreatorUser baseCreatorUser) {
		return baseCreatorUserMapper.update(baseCreatorUser);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		BaseCreatorUser temp = new BaseCreatorUser();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(BaseCreatorUser baseCreatorUser) {
		return this.delete(baseCreatorUser.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseCreatorUserMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseCreatorUserMapper.deleteByIds(ids);
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
	public List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser) {
		return baseCreatorUserMapper.findList(baseCreatorUser);
	}
	
	@Override
	public List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, String... orderByStrArray) {
		return this.findList(baseCreatorUser, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, Order... orders) {
		return this.findList(baseCreatorUser, new Pagination(orders));
	}

	@Override
	public List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, List<Order> orders) {
		return this.findList(baseCreatorUser, new Pagination(orders));
	}
	
	private List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseCreatorUser, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseCreatorUser baseCreatorUser) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseCreatorUser, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize) {
		return this.findPage(baseCreatorUser, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseCreatorUser, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseCreatorUser, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseCreatorUser baseCreatorUser, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseCreatorUser, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseCreatorUser baseCreatorUser, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseCreatorUser> list = baseCreatorUserMapper.findList(baseCreatorUser);
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
