package com.github.hualuomoli.plugin.roll.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollErrorHis;
import com.github.hualuomoli.plugin.roll.entity.PollErrorHis;

// #BasePollErrorHis
public interface BasePollErrorHisService {

	BasePollErrorHis get(PollErrorHis pollErrorHis);
	
	BasePollErrorHis get(String id);
	
	
	int insert(PollErrorHis pollErrorHis);
	
	<T extends PollErrorHis> int batchInsert(List<T> list);

	int update(PollErrorHis pollErrorHis);
	
	

	int delete(PollErrorHis pollErrorHis);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis);
	
	List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, String... orderByStrArray);

	List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, Order... orders);

	List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, List<Order> orders);
	
	Integer getTotal(BasePollErrorHis basePollErrorHis);
	
	Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize);

	Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BasePollErrorHis basePollErrorHis, Pagination pagination);
	
}
