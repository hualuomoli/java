package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.orm.stereotype.Mapper;
import com.github.hualuomoli.demo.base.entity.Demo;

@Mapper(value = "com.github.hualuomoli.demo.base.mapper.DemoMapper")
public interface DemoMapper {

	// 根据主键获取实体类
	Demo get(Demo demo);

	// 根据主键获取实体类
	Demo get(String id);

	// 添加
	int insert(Demo demo);

	// 批量添加
	int batchInsert(/* @Param(value = "list") */ List<Demo> list);

	// 修改
	int update(Demo demo);

	// 根据主键删除
	int delete(Demo demo);

	// 根据主键删除
	int delete(String id);

	// 批量删除
	int deleteByIds(String[] ids);

	// 批量删除
	int deleteByIds(Collection<String> ids);

	// 查询列表
	List<Demo> findList(Demo demo);
}
