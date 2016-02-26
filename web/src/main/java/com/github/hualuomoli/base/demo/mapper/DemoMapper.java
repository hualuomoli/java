package com.github.hualuomoli.base.demo.mapper;

import java.util.List;

import com.github.hualuomoli.base.demo.entity.Demo;
import com.github.hualuomoli.base.stereotype.Mapper;

@Mapper
public interface DemoMapper {

	Demo get(String id);

	int insert(Demo demo);

	int update(Demo demo);

	int delete(String id);

	List<Demo> findList(Demo demo);

}
