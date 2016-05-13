package com.github.hualuomoli.demo.raml.app.delete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteNoparamEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.delete.mapper.DeleteMapper;


/**
 * @Description 删除
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:02
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.delete.DeleteService")
@Transactional(readOnly = true)
public class DeleteService {

	protected static final Logger logger = LoggerFactory.getLogger(DeleteService.class);

	@Autowired
	protected DeleteMapper deleteMapper;
	
	@Transactional(readOnly = false)
	public
	void
	deleteNoparam(DeleteNoparamEntity deleteNoparamEntity) {
		// TODO
	}
	@Transactional(readOnly = false)
	public
	void
	deleteUriparamById(DeleteUriparamByIdEntity deleteUriparamByIdEntity) {
		// TODO
	}

}
