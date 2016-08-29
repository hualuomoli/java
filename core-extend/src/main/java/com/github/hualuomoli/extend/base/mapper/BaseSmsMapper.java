package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseSms;

// #BaseSms
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseSmsMapper")
public interface BaseSmsMapper {

	
	BaseSms get(String id);

	int insert(BaseSms baseSms);
	
	int batchInsert(@Param(value = "list") List<BaseSms> list);

	int update(BaseSms baseSms);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseSms> findList(BaseSms baseSms);

}
