package com.github.hualuomoli.plugin.roll.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollData;
import com.github.hualuomoli.plugin.roll.entity.PollData;

// #BasePollData
public interface BasePollDataService {

	BasePollData get(PollData pollData);
	
	BasePollData get(String id);
	
	
	int insert(PollData pollData);
	
	<T extends PollData> int batchInsert(List<T> list);

	int update(PollData pollData);
	
	

	int delete(PollData pollData);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BasePollData> findList(BasePollData basePollData);
	
	List<BasePollData> findList(BasePollData basePollData, String... orderByStrArray);

	List<BasePollData> findList(BasePollData basePollData, Order... orders);

	List<BasePollData> findList(BasePollData basePollData, List<Order> orders);
	
	Integer getTotal(BasePollData basePollData);
	
	Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize);

	Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BasePollData basePollData, Pagination pagination);
	
}
