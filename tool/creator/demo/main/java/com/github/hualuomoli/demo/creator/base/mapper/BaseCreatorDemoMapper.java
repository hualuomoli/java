package com.github.hualuomoli.demo.creator.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorDemo;

// #BaseCreatorDemo
@Repository(value = "com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorDemoMapper")
public interface BaseCreatorDemoMapper {

	
	BaseCreatorDemo get(String id);

	int insert(BaseCreatorDemo baseCreatorDemo);
	
	int batchInsert(@Param(value = "list") List<BaseCreatorDemo> list);

	int update(BaseCreatorDemo baseCreatorDemo);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseCreatorDemo> findList(BaseCreatorDemo baseCreatorDemo);

}
