package com.github.hualuomoli.system.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.system.base.entity.BaseUploadFile;

// #BaseUploadFile
@Repository(value = "com.github.hualuomoli.system.base.mapper.BaseUploadFileMapper")
public interface BaseUploadFileMapper {

	
	BaseUploadFile get(String id);

	int insert(BaseUploadFile baseUploadFile);
	
	int batchInsert(@Param(value = "list") List<BaseUploadFile> list);

	int update(BaseUploadFile baseUploadFile);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile);

}
