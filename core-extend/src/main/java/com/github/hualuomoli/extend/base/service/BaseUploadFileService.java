package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.tree.service.TreeService.TreeDealer;

// #BaseUploadFile
public interface BaseUploadFileService {

	BaseUploadFile get(BaseUploadFile baseUploadFile);
	
	BaseUploadFile get(String id);
	
	
	int insert(BaseUploadFile baseUploadFile);
	
	int batchInsert(List<BaseUploadFile> list);

	int update(BaseUploadFile baseUploadFile);
	
	

	int delete(BaseUploadFile baseUploadFile);
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile);
	
	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, String... orderByStrArray);

	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, Order... orders);

	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile, List<Order> orders);
	
	Integer getTotal(BaseUploadFile baseUploadFile);
	
	Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize);

	Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(BaseUploadFile baseUploadFile, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(BaseUploadFile baseUploadFile, Pagination pagination);
	
}
