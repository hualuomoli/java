package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseSms;

// #BaseSms
public interface BaseSmsService {

	BaseSms get(BaseSms baseSms);
	
	BaseSms get(String id);
	
	
	int insert(BaseSms baseSms);
	
	int batchInsert(List<BaseSms> list);

	int update(BaseSms baseSms);
	
	 int logicalDelete(BaseSms baseSms);

	 int logicalDelete(String id);
	

	int delete(BaseSms baseSms);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseSms> findList(BaseSms baseSms);
	
	List<BaseSms> findList(BaseSms baseSms, String... orderByStrArray);

	List<BaseSms> findList(BaseSms baseSms, Order... orders);

	List<BaseSms> findList(BaseSms baseSms, List<Order> orders);
	
	Integer getTotal(BaseSms baseSms);
	
	Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize);

	Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseSms baseSms, Pagination pagination);
	
}
