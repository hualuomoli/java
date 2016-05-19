package com.github.hualuomoli.demo.raml.app.urlEncoded.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.mapper.UrlEncodedMapper;


/**
 * @Description 表单提交
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.urlEncoded.UrlEncodedService")
@Transactional(readOnly = true)
public class UrlEncodedService {

	protected static final Logger logger = LoggerFactory.getLogger(UrlEncodedService.class);

	@Autowired
	protected UrlEncodedMapper urlEncodedMapper;
	
	@Transactional(readOnly = false)
	public
	void
	postNoparam(PostNoparamEntity postNoparamEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	postUriparamById(PostUriparamByIdEntity postUriparamByIdEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	postUriformparamByPageNumberPageSize(PostUriformparamByPageNumberPageSizeUrlEncodedEntity postUriformparamByPageNumberPageSizeUrlEncodedEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	postFormparam(PostFormparamUrlEncodedEntity postFormparamUrlEncodedEntity) {
		// TODO
	}

}
