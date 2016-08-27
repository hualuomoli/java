package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseProduct;

// #BaseProduct
public interface BaseProductService {

	BaseProduct get(BaseProduct baseProduct);
	
	BaseProduct get(String id);
	
	
	int insert(BaseProduct baseProduct);
	
	int batchInsert(List<BaseProduct> list);

	int update(BaseProduct baseProduct);
	
	 int logicalDelete(BaseProduct baseProduct);

	 int logicalDelete(String id);
	

	int delete(BaseProduct baseProduct);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseProduct> findList(BaseProduct baseProduct);
	
	List<BaseProduct> findList(BaseProduct baseProduct, String... orderByStrArray);

	List<BaseProduct> findList(BaseProduct baseProduct, Order... orders);

	List<BaseProduct> findList(BaseProduct baseProduct, List<Order> orders);
	
	Integer getTotal(BaseProduct baseProduct);
	
	Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize);

	Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseProduct baseProduct, Pagination pagination);
	
}
