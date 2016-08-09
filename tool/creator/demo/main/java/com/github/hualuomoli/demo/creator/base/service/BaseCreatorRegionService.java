package com.github.hualuomoli.demo.creator.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorRegion;

// #BaseCreatorRegion
public interface BaseCreatorRegionService {

	BaseCreatorRegion get(BaseCreatorRegion baseCreatorRegion);
	
	BaseCreatorRegion get(String id);
	
	BaseCreatorRegion getUnique(
		java.lang.String code,
		java.lang.Integer type
	);
	
	int insert(BaseCreatorRegion baseCreatorRegion);
	
	int batchInsert(List<BaseCreatorRegion> list);

	int update(BaseCreatorRegion baseCreatorRegion);
	
	

	int delete(BaseCreatorRegion baseCreatorRegion);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion);
	
	List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, String... orderByStrArray);

	List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, Order... orders);

	List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, List<Order> orders);
	
	Integer getTotal(BaseCreatorRegion baseCreatorRegion);
	
	Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize);

	Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseCreatorRegion baseCreatorRegion, Pagination pagination);
	
}
