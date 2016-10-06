package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseUploadFile;
import com.github.hualuomoli.extend.entity.UploadFile;

// #BaseUploadFile
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseUploadFileMapper")
public interface BaseUploadFileMapper {

	
	BaseUploadFile get(String id);

	int insert(UploadFile uploadFile);
	
	<T extends UploadFile> int batchInsert(@Param(value = "list") List<T> list);

	int update(UploadFile uploadFile);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseUploadFile> findList(BaseUploadFile baseUploadFile);

}
