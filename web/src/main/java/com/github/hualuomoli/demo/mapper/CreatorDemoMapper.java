package com.github.hualuomoli.demo.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.hualuomoli.base.stereotype.Mapper;
import com.github.hualuomoli.demo.entity.CreatorDemo;

// #CreatorDemo
@Mapper(value = "com.github.hualuomoli.demo.mapper.CreatorDemoMapper")
public interface CreatorDemoMapper {

	CreatorDemo get(String id);

	int insert(CreatorDemo creatorDemo);
	
	int batchInsert(List<CreatorDemo> list);

	int update(CreatorDemo creatorDemo);

	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<CreatorDemo> findList(CreatorDemo creatorDemo);

}
