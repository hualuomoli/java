package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.base.entity.BaseDemo;

// #BaseDemo
@Repository(value = "com.github.hualuomoli.demo.base.mapper.BaseDemoMapper")
public interface BaseDemoMapper {

	BaseDemo get(BaseDemo baseDemo);
	
	BaseDemo get(String id);

	int insert(BaseDemo baseDemo);
	
	int batchInsert(@Param(value = "list") List<BaseDemo> list);

	int update(BaseDemo baseDemo);

	int delete(BaseDemo baseDemo);
	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<BaseDemo> findList(BaseDemo baseDemo);

}
