package com.github.hualuomoli.extend.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.entity.UploadFile;

// #BaseUploadFile
public interface BaseUploadFileService {

	BaseUploadFile get(UploadFile uploadFile);
	
	BaseUploadFile get(String id);
	
	
	int insert(UploadFile uploadFile);
	
	<T extends UploadFile> int batchInsert(List<T> list);

	int update(UploadFile uploadFile);
	
	

	int delete(UploadFile uploadFile);
	
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
