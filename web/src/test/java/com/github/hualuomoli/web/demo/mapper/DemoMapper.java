package com.github.hualuomoli.web.demo.mapper;

import java.util.List;

import com.github.hualuomoli.base.stereotype.Mapper;
import com.github.hualuomoli.web.demo.entity.Demo;

@Mapper
public interface DemoMapper {

	Demo get(String id);

	int insert(Demo demo);

	int update(Demo demo);

	int delete(String id);

	List<Demo> findList(Demo demo);

}
