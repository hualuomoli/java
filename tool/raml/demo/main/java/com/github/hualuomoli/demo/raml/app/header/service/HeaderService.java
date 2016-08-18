package com.github.hualuomoli.demo.raml.app.header.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.header.entity.GetGetEntity;
import com.github.hualuomoli.demo.raml.app.header.entity.GetGetResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.header.mapper.HeaderMapper;


/**
 * @Description 增加header验证
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.header.HeaderService")
@Transactional(readOnly = true)
public class HeaderService {

	protected static final Logger logger = LoggerFactory.getLogger(HeaderService.class);

	@Autowired
	protected HeaderMapper headerMapper;
	
	/**
	 * 查询
	 */
	public
	void
	getGet(GetGetEntity getGetEntity) {
		// TODO
	}

}
