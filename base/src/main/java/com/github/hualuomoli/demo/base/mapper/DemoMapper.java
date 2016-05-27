package com.github.hualuomoli.demo.base.mapper;

import java.util.List;

import com.github.hualuomoli.base.orm.stereotype.Mapper;
import com.github.hualuomoli.demo.base.entity.Demo;

@Mapper(value = "com.github.hualuomoli.demo.base.mapper.DemoMapper")
public interface DemoMapper {

	Demo get(String id);

	int insert(Demo demo);

	int update(Demo demo);

	int delete(String id);

	List<Demo> findList(Demo demo);

}
