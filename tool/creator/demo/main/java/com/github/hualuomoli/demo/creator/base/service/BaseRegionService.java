package com.github.hualuomoli.demo.creator.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.demo.creator.base.entity.BaseRegion;

// #BaseRegion
public interface BaseRegionService {

	BaseRegion get(BaseRegion baseRegion);
	
	BaseRegion get(String id);
	
	BaseRegion getUnique(
		java.lang.String code,
		java.lang.Integer type
	);
	
	void insert(BaseRegion baseRegion);
	
	void batchInsert(List<BaseRegion> list);

	void update(BaseRegion baseRegion);
	
	void logicalDelete(BaseRegion baseRegion);

	void logicalDelete(String id);

	void delete(BaseRegion baseRegion);
	
	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

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
