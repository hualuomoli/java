package com.github.hualuomoli.demo.service;

import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.entity.Demo;

// #Demo
public interface DemoService {

	Demo get(String id);

	int insert(Demo demo);

	List<Demo> findList(Demo demo);

	Page findPage(Demo demo, Integer pageNumber, Integer pageSize);

}
