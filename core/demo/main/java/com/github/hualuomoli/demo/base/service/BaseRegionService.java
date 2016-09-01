package com.github.hualuomoli.demo.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.BaseRegion;
import com.github.hualuomoli.demo.entity.Region;

// #BaseRegion
public interface BaseRegionService {

	BaseRegion get(Region region);
	
	BaseRegion get(String id);
	
	BaseRegion getUnique(
		java.lang.String code,
		java.lang.Integer type
	);
	
	int insert(Region region);
	
	<T extends Region> int batchInsert(List<T> list);

	int update(Region region);
	
	

	int delete(Region region);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseRegion> findList(BaseRegion baseRegion);
	
	List<BaseRegion> findList(BaseRegion baseRegion, String... orderByStrArray);

	List<BaseRegion> findList(BaseRegion baseRegion, Order... orders);

	List<BaseRegion> findList(BaseRegion baseRegion, List<Order> orders);
	
	Integer getTotal(BaseRegion baseRegion);
	
	Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize);

	Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseRegion baseRegion, Pagination pagination);
	
}
