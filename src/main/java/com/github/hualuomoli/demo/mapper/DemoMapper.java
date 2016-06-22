package com.github.hualuomoli.demo.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.entity.Demo;

// #Demo
@Repository(value = "com.github.hualuomoli.demo.mapper.DemoMapper")
public interface DemoMapper {

	Demo get(String id);

	int insert(Demo demo);

	List<Demo> findList(Demo demo);

}
