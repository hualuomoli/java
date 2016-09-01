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
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorDemo;
import com.github.hualuomoli.demo.creator.entity.CreatorDemo;
import com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorDemoMapper;
import com.github.hualuomoli.demo.creator.base.service.BaseCreatorDemoService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseCreatorDemo
@Service(value = "com.github.hualuomoli.demo.creator.base.service.BaseCreatorDemoServiceImpl")
@Transactional(readOnly = true)
public class BaseCreatorDemoServiceImpl implements BaseCreatorDemoService {

	@Autowired
	private BaseCreatorDemoMapper baseCreatorDemoMapper;
	
	@Override
	public BaseCreatorDemo get(CreatorDemo creatorDemo) {
		return this.get(creatorDemo.getId());
	}
	
	@Override
	public BaseCreatorDemo get(String id) {
		return baseCreatorDemoMapper.get(id);
	}
	
	@Override
	public BaseCreatorDemo getUnique(
		java.lang.String name
	) {
		BaseCreatorDemo baseCreatorDemo = new BaseCreatorDemo();
		baseCreatorDemo.setName(name);
		List<BaseCreatorDemo> list = this.findList(baseCreatorDemo);
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
	public int insert(@PreInsert CreatorDemo creatorDemo) {
		return baseCreatorDemoMapper.insert(creatorDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends CreatorDemo> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += baseCreatorDemoMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate CreatorDemo creatorDemo) {
		return baseCreatorDemoMapper.update(creatorDemo);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete CreatorDemo creatorDemo) {
		return baseCreatorDemoMapper.update(creatorDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		CreatorDemo temp = new CreatorDemo();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(CreatorDemo creatorDemo) {
		return this.delete(creatorDemo.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseCreatorDemoMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseCreatorDemoMapper.deleteByIds(ids);
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
	public List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo) {
		return baseCreatorDemoMapper.findList(baseCreatorDemo);
	}
	
	@Override
	public List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, String... orderByStrArray) {
		return this.findList(baseCreatorDemo, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, Order... orders) {
		return this.findList(baseCreatorDemo, new Pagination(orders));
	}

	@Override
	public List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, List<Order> orders) {
		return this.findList(baseCreatorDemo, new Pagination(orders));
	}
	
	private List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseCreatorDemo, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseCreatorDemo baseCreatorDemo) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseCreatorDemo, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize) {
		return this.findPage(baseCreatorDemo, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseCreatorDemo, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseCreatorDemo, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseCreatorDemo baseCreatorDemo, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseCreatorDemo, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseCreatorDemo baseCreatorDemo, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseCreatorDemo> list = baseCreatorDemoMapper.findList(baseCreatorDemo);
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
