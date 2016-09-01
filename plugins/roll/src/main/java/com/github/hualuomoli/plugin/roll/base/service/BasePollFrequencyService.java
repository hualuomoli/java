package com.github.hualuomoli.plugin.roll.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollFrequency;
import com.github.hualuomoli.plugin.roll.entity.PollFrequency;

// #BasePollFrequency
public interface BasePollFrequencyService {

	BasePollFrequency get(PollFrequency pollFrequency);
	
	BasePollFrequency get(String id);
	
	BasePollFrequency getUnique(
		java.lang.String dataId
	);
	
	int insert(PollFrequency pollFrequency);
	
	<T extends PollFrequency> int batchInsert(List<T> list);

	int update(PollFrequency pollFrequency);
	
	

	int delete(PollFrequency pollFrequency);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BasePollFrequency> findList(BasePollFrequency basePollFrequency);
	
	List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, String... orderByStrArray);

	List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, Order... orders);

	List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, List<Order> orders);
	
	Integer getTotal(BasePollFrequency basePollFrequency);
	
	Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize);

	Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BasePollFrequency basePollFrequency, Pagination pagination);
	
}
