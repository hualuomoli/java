package com.github.hualuomoli.plugin.roll.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollSuccessHis;
import com.github.hualuomoli.plugin.roll.entity.PollSuccessHis;

// #BasePollSuccessHis
public interface BasePollSuccessHisService {

	BasePollSuccessHis get(PollSuccessHis pollSuccessHis);
	
	BasePollSuccessHis get(String id);
	
	
	int insert(PollSuccessHis pollSuccessHis);
	
	<T extends PollSuccessHis> int batchInsert(List<T> list);

	int update(PollSuccessHis pollSuccessHis);
	
	

	int delete(PollSuccessHis pollSuccessHis);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis);
	
	List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, String... orderByStrArray);

	List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, Order... orders);

	List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, List<Order> orders);
	
	Integer getTotal(BasePollSuccessHis basePollSuccessHis);
	
	Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize);

	Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BasePollSuccessHis basePollSuccessHis, Pagination pagination);
	
}
