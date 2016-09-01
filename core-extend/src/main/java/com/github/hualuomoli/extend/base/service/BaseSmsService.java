package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseSms;
import com.github.hualuomoli.extend.entity.Sms;

// #BaseSms
public interface BaseSmsService {

	BaseSms get(Sms sms);
	
	BaseSms get(String id);
	
	
	int insert(Sms sms);
	
	<T extends Sms> int batchInsert(List<T> list);

	int update(Sms sms);
	
	 int logicalDelete(Sms sms);

	 int logicalDelete(String id);
	

	int delete(Sms sms);
	
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
