package com.github.hualuomoli.demo.base.service.impl;

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
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.demo.base.entity.BaseDemo;
import com.github.hualuomoli.demo.base.mapper.BaseDemoMapper;
import com.github.hualuomoli.demo.base.service.BaseDemoService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseDemo
@Service(value = "com.github.hualuomoli.demo.base.service.BaseDemoServiceImpl")
@Transactional(readOnly = true)
public class BaseDemoServiceImpl implements BaseDemoService {

	@Autowired
	private BaseDemoMapper baseDemoMapper;
	
	@Override
	public BaseDemo get(BaseDemo baseDemo) {
		return this.get(baseDemo.getId());
	}
	
	@Override
	public BaseDemo get(String id) {
		return baseDemoMapper.get(id);
	}
	
	@Override
	public BaseDemo getByName(java.lang.String name) {
		BaseDemo baseDemo = new BaseDemo();
		baseDemo.setName(name);
		List<BaseDemo> list = this.findList(baseDemo);
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
	public void insert(@PreInsert BaseDemo baseDemo) {
		baseDemoMapper.insert(baseDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void batchInsert(@PreBatchInsert  List<BaseDemo> list) {
		if (list == null || list.size() == 0) {
			return;
		}	
		
		Config config = new Config(100);
		while (true) {
			List<BaseDemo> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			baseDemoMapper.batchInsert(newList);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(@PreUpdate BaseDemo baseDemo) {
		baseDemoMapper.update(baseDemo);
	}

	@Override
	@Transactional(readOnly = false)
	public void logicalDelete(@PreDelete BaseDemo baseDemo) {
		baseDemoMapper.update(baseDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void logicalDelete(String id) {
		BaseDemo temp = new BaseDemo();
		temp.setId(id);
		this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(BaseDemo baseDemo) {
		this.delete(baseDemo.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		baseDemoMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		baseDemoMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(Collection<String> ids) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		this.deleteByIds(ids.toArray(new String[]{}));
	}

	@Override
	public List<BaseDemo> findList(BaseDemo baseDemo) {
		return baseDemoMapper.findList(baseDemo);
	}
	
	@Override
	public Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize) {
		return this.findPage(baseDemo, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseDemo, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseDemo, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseDemo baseDemo, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseDemo, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseDemo baseDemo, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseDemo> list = baseDemoMapper.findList(baseDemo);
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
