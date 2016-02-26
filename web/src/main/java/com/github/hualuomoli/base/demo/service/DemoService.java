package com.github.hualuomoli.base.demo.service;

import java.util.List;

import com.github.hualuomoli.base.demo.entity.Demo;
import com.github.hualuomoli.base.entity.Pagination;

public interface DemoService {

	Demo get(String id);

	void insert(Demo demo);

	void update(Demo demo);

	void delete(String id);

	List<Demo> findList(Demo demo);

	Pagination findPage(Demo demo, Pagination pagination);

}
