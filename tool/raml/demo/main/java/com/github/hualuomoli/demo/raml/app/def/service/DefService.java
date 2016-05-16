package com.github.hualuomoli.demo.raml.app.def.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostByIdFileEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostjsonByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostjsonByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.mapper.DefMapper;


/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:46
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.def.DefService")
@Transactional(readOnly = true)
public class DefService {

	protected static final Logger logger = LoggerFactory.getLogger(DefService.class);

	@Autowired
	protected DefMapper defMapper;
	
	@Transactional(readOnly = false)
	public
	void
	postPostById(PostPostByIdFileEntity postPostByIdFileEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	postPostjsonById(PostPostjsonByIdJsonEntity postPostjsonByIdJsonEntity) {
		// TODO
	}

}
