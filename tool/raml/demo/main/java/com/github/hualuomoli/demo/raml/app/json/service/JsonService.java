package com.github.hualuomoli.demo.raml.app.json.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.json.entity.PostUserByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.json.entity.PostUserByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.json.mapper.JsonMapper;


/**
 * @Description JSON数据
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.json.JsonService")
@Transactional(readOnly = true)
public class JsonService {

	protected static final Logger logger = LoggerFactory.getLogger(JsonService.class);

	@Autowired
	protected JsonMapper jsonMapper;
	
	@Transactional(readOnly = false)
	public
	void
	postUserById(PostUserByIdJsonEntity postUserByIdJsonEntity) {
		// TODO
	}

}
