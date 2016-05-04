package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.Demo;

// #Demo
public interface DemoService {

	Demo get(Demo demo);
	
	Demo get(String id);

	int insert(Demo demo);
	
	int batchInsert(List<Demo> list);

	int update(Demo demo);

	int delete(Demo demo);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<Demo> findList(Demo demo);

	Pagination findPage(Demo demo, Pagination pagination);
	
}
