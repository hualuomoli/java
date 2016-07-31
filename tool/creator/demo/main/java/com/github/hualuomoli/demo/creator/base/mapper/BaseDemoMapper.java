package com.github.hualuomoli.demo.creator.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.creator.base.entity.BaseDemo;

// #BaseDemo
@Repository(value = "com.github.hualuomoli.demo.creator.base.mapper.BaseDemoMapper")
public interface BaseDemoMapper {

	
	BaseDemo get(String id);

	int insert(BaseDemo baseDemo);
	
	int batchInsert(@Param(value = "list") List<BaseDemo> list);

	int update(BaseDemo baseDemo);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseDemo> findList(BaseDemo baseDemo);

}
