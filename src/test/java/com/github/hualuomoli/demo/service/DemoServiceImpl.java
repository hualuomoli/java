package com.github.hualuomoli.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.entity.Demo;
import com.github.hualuomoli.demo.mapper.DemoMapper;
import com.github.hualuomoli.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.mybatis.interceptor.pagination.PaginationInterceptor;

// #Demo
@Service(value = "com.github.hualuomoli.demo.service.DemoServiceImpl")
@Transactional(readOnly = true)
public class DemoServiceImpl implements DemoService {

	@Autowired
	private DemoMapper demoMapper;

	@Override
	public Demo get(String id) {
		return demoMapper.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(Demo demo) {
		return demoMapper.insert(demo);
	}

	@Override
	public List<Demo> findList(Demo demo) {
		return demoMapper.findList(demo);
	}

	@Override
	public Page findPage(Demo demo, Integer pageNumber, Integer pageSize) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNumber);
		pagination.setPageSize(pageSize);

		// set local thread
		PaginationInterceptor.setPagination(pagination);
		// query
		List<Demo> list = demoMapper.findList(demo);
		// get local thread
		pagination = PaginationInterceptor.getPagination();

		// set page
		Page page = new Page();
		page.setCount(pagination.getCount());
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		page.setDataList(list);

		return page;
	}

}
