package com.github.hualuomoli.demo.raml.app.complex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.complex.mapper.ComplexMapper;


/**
 * @Description 复杂json参数
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:02
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.complex.ComplexService")
@Transactional(readOnly = true)
public class ComplexService {

	protected static final Logger logger = LoggerFactory.getLogger(ComplexService.class);

	@Autowired
	protected ComplexMapper complexMapper;
	
	@Transactional(readOnly = false)
	public
	PostUriformByIdResultJsonEntityUser
	postUriformById(PostUriformByIdJsonEntity postUriformByIdJsonEntity) {
		// TODO
		PostUriformByIdResultJsonEntityUser obj = JsonUtils.parseObject("{\"username\":\"hualuomoli\",\"address\":{\"name\":\"合肥路666号,永旺广场\",\"couty\":\"03\",\"province\":\"37\",\"city\":\"02\"},\"nickname\":\"花落莫离\",\"orders\":[{\"id\":\"1234\",\"date\":\"2015-06-05\",\"products\":[{\"id\":\"0001\",\"name\":\"IPAD\"}]}]}", PostUriformByIdResultJsonEntityUser.class);
		return obj;
	}

}
