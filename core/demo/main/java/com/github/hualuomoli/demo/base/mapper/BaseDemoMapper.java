package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.base.entity.BaseDemo;
import com.github.hualuomoli.demo.entity.Demo;

// #BaseDemo
@Repository(value = "com.github.hualuomoli.demo.base.mapper.BaseDemoMapper")
public interface BaseDemoMapper {

	
	BaseDemo get(String id);

	int insert(Demo demo);
	
	<T extends Demo> int batchInsert(@Param(value = "list") List<T> list);

	int update(Demo demo);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseDemo> findList(BaseDemo baseDemo);

}
