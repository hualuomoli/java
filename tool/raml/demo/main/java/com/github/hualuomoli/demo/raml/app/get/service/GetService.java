package com.github.hualuomoli.demo.raml.app.get.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.mapper.GetMapper;


/**
 * @Description get方式请求
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:01
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.get.GetService")
@Transactional(readOnly = true)
public class GetService {

	protected static final Logger logger = LoggerFactory.getLogger(GetService.class);

	@Autowired
	protected GetMapper getMapper;
	
	public
	void
	getUriparamById(GetUriparamByIdEntity getUriparamByIdEntity) {
		// TODO
	}
	public
	void
	getQueryparam(GetQueryparamEntity getQueryparamEntity) {
		// TODO
	}
	public
	void
	getUriqueryparamByPageNumberPageSize(GetUriqueryparamByPageNumberPageSizeEntity getUriqueryparamByPageNumberPageSizeEntity) {
		// TODO
	}
	public
	void
	getNoparam(GetNoparamEntity getNoparamEntity) {
		// TODO
	}

}
