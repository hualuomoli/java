package com.github.hualuomoli.demo.raml.app.repeat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileFileEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.mapper.RepeatMapper;


/**
 * @Description 重复值
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.repeat.RepeatService")
@Transactional(readOnly = true)
public class RepeatService {

	protected static final Logger logger = LoggerFactory.getLogger(RepeatService.class);

	@Autowired
	protected RepeatMapper repeatMapper;
	
	@Transactional(readOnly = false)
	public
	PostJsonResultJsonEntityUser
	postJson(PostJsonJsonEntity postJsonJsonEntity) {
		// TODO
		PostJsonResultJsonEntityUser obj = JsonUtils.parseObject("{\"username\":\"hualuomoli\",\"address\":{\"name\":\"合肥路666号,永旺广场\",\"couty\":\"03\",\"province\":\"37\",\"city\":\"02\"},\"nickname\":\"花落莫离\",\"orders\":[{\"id\":\"1234\",\"date\":\"2015-06-05\",\"products\":[{\"id\":\"0001\",\"name\":\"IPAD\"}]}]}", PostJsonResultJsonEntityUser.class);
		return obj;
	}
	@Transactional(readOnly = false)
	public
	void
	postUrlencoded(PostUrlencodedUrlEncodedEntity postUrlencodedUrlEncodedEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	postFile(PostFileFileEntity postFileFileEntity) {
		// TODO
	}

}
