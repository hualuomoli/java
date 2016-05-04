package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.Demo;

public interface DemoService {

	// 根据主键获取实体类
	Demo get(Demo demo);

	// 根据主键获取实体类
	Demo get(String id);

	// 添加
	void insert(Demo demo);

	// 批量添加
	void batchInsert(List<Demo> list);

	// 修改
	void update(Demo demo);

	// 根据主键删除
	void delete(Demo demo);

	// 根据主键删除
	void delete(String id);

	// 批量删除
	void deleteByIds(String[] ids);

	// 批量删除
	void deleteByIds(Collection<String> ids);

	// 查询列表
	List<Demo> findList(Demo demo);

	// 查询分页
	Pagination findPage(Demo demo, Pagination pagination);

}
