package com.github.hualuomoli.web.demo.service;

import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.web.demo.entity.Demo;

public interface DemoService {

	Demo get(String id);

	void insert(Demo demo);

	void update(Demo demo);

	void delete(String id);

	List<Demo> findList(Demo demo);

	Pagination findPage(Demo demo, Pagination pagination);

}
