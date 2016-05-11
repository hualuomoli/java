package com.github.hualuomoli.demo.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.entity.CreatorDemo;

// #CreatorDemo
public interface CreatorDemoService {

	CreatorDemo get(String id);

	void insert(CreatorDemo creatorDemo);
	
	void batchInsert(List<CreatorDemo> list);

	void update(CreatorDemo creatorDemo);

	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

	List<CreatorDemo> findList(CreatorDemo creatorDemo);

	Pagination findPage(CreatorDemo creatorDemo, Pagination pagination);
	
}
