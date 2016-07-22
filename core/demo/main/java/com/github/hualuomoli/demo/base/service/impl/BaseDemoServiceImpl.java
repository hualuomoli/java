package com.github.hualuomoli.demo.base.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PrePersistent;
import com.github.hualuomoli.base.annotation.persistent.Type;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.demo.base.entity.BaseDemo;
import com.github.hualuomoli.demo.base.mapper.BaseDemoMapper;
import com.github.hualuomoli.demo.base.service.BaseDemoService;


// #BaseDemo
@Service(value = "com.github.hualuomoli.demo.base.service.BaseDemoServiceImpl")
@Transactional(readOnly = true)
public class BaseDemoServiceImpl implements BaseDemoService {

	@Autowired
	private BaseDemoMapper baseDemoMapper;
	
	@Override
	public BaseDemo get(BaseDemo baseDemo) {
		return baseDemoMapper.get(baseDemo);
	}
	
	@Override
	public BaseDemo get(String id) {
		return baseDemoMapper.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(@PrePersistent(type = Type.INSERT) BaseDemo baseDemo) {
		return baseDemoMapper.insert(baseDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PrePersistent(type = Type.BATCH_INSERT)  List<BaseDemo> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		
		Config config = new Config(100);
		while (true) {
			List<BaseDemo> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			baseDemoMapper.batchInsert(newList);
		}
		return list.size();
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PrePersistent(type = Type.UPDATE)  BaseDemo baseDemo) {
		return baseDemoMapper.update(baseDemo);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(BaseDemo baseDemo) {
		BaseDemo temp = new BaseDemo();
		temp.setId(baseDemo.getId());
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		BaseDemo temp = new BaseDemo();
		temp.setId(id);
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(BaseDemo baseDemo) {
		return baseDemoMapper.delete(baseDemo);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseDemoMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		return baseDemoMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		return baseDemoMapper.deleteByIds(ids);
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
