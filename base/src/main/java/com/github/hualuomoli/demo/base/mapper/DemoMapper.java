package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.hualuomoli.base.orm.stereotype.Mapper;
import com.github.hualuomoli.demo.base.entity.Demo;

// #Demo
@Mapper(value = "com.github.hualuomoli.demo.base.mapper.DemoMapper")
public interface DemoMapper {

	Demo get(Demo demo);
	
	Demo get(String id);

	int insert(Demo demo);
	
	int batchInsert(List<Demo> list);

	int update(Demo demo);

	int delete(Demo demo);
	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<Demo> findList(Demo demo);

}
