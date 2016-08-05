package com.github.hualuomoli.demo.raml.app.origin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetListEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetListResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.origin.mapper.OriginMapper;


/**
 * @Description 包括省市区、养殖类型、养殖种类等基础数据
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.origin.OriginService")
@Transactional(readOnly = true)
public class OriginService {

	protected static final Logger logger = LoggerFactory.getLogger(OriginService.class);

	@Autowired
	protected OriginMapper originMapper;
	
	/**
	 * 
	 */
	public
	java.util.List<GetListResultJsonEntity>
	getList(GetListEntity getListEntity) {
		// TODO
		java.util.List<GetListResultJsonEntity> list = JsonUtils.parseList("[{\"value\":\"370000\",\"label\":\"山东省\"},{\"value\":\"110000\",\"label\":\"北京市\"}]", GetListResultJsonEntity.class);
		return list;
	}
	/**
	 * get
	 */
	public
	GetObjectResultJsonEntity
	getObject(GetObjectEntity getObjectEntity) {
		// TODO
		GetObjectResultJsonEntity obj = JsonUtils.parseObject("{\"sex\":\"M\",\"username\":\"hualuomoli\",\"address\":{\"county\":\"03\",\"province\":\"37\",\"city\":\"02\"},\"nickname\":\"花落莫离\",\"age\":20,\"orders\":[{\"id\":\"123456789\",\"price\":123.45,\"name\":\"Ipad\"},{\"id\":\"1234567890\",\"price\":6666.88,\"name\":\"IMAC\"}]}", GetObjectResultJsonEntity.class);
		return obj;
	}

}
