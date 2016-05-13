package com.github.hualuomoli.demo.raml.app.multipart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.multipart.entity.PostFileByIdFileEntity;
import com.github.hualuomoli.demo.raml.app.multipart.entity.PostFileByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.multipart.mapper.MultipartMapper;


/**
 * @Description 文件上传
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:02
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.multipart.MultipartService")
@Transactional(readOnly = true)
public class MultipartService {

	protected static final Logger logger = LoggerFactory.getLogger(MultipartService.class);

	@Autowired
	protected MultipartMapper multipartMapper;
	
	@Transactional(readOnly = false)
	public
	void
	postFileById(PostFileByIdFileEntity postFileByIdFileEntity) {
		// TODO
	}

}
