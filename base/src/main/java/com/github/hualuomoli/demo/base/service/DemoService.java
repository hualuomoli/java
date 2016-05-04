package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.Demo;

// #Demo
public interface DemoService {

	Demo get(Demo demo);
	
	Demo get(String id);

	void insert(Demo demo);
	
	void batchInsert(List<Demo> list);

	void update(Demo demo);

	void delete(Demo demo);
	
	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

	List<Demo> findList(Demo demo);

	Pagination findPage(Demo demo, Pagination pagination);
	
}
