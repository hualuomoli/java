package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseSms;
import com.github.hualuomoli.extend.entity.Sms;

// #BaseSms
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseSmsMapper")
public interface BaseSmsMapper {

	
	BaseSms get(String id);

	int insert(Sms sms);
	
	<T extends Sms> int batchInsert(@Param(value = "list") List<T> list);

	int update(Sms sms);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseSms> findList(BaseSms baseSms);

}
