package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.base.entity.BaseDemo;

// #BaseDemo
public interface BaseDemoService {

	BaseDemo get(BaseDemo baseDemo);
	
	BaseDemo get(String id);

	int insert(BaseDemo baseDemo);
	
	int batchInsert(List<BaseDemo> list);

	int update(BaseDemo baseDemo);

	int delete(BaseDemo baseDemo);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseDemo> findList(BaseDemo baseDemo);

	Page findPage(BaseDemo baseDemo, Integer pageNumber, Integer pageSize);
	
}
