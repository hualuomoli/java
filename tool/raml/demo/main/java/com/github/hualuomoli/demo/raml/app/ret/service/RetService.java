package com.github.hualuomoli.demo.raml.app.ret.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.mapper.RetMapper;


/**
 * @Description 响应
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:44
 * @Version 1.0
 */
@Service(value = "com.github.hualuomoli.demo.raml.app.ret.RetService")
@Transactional(readOnly = true)
public class RetService {

	protected static final Logger logger = LoggerFactory.getLogger(RetService.class);

	@Autowired
	protected RetMapper retMapper;
	
	public
	GetObjectdataResultJsonEntityUser
	getObjectdata(GetObjectdataEntity getObjectdataEntity) {
		// TODO
		GetObjectdataResultJsonEntityUser obj = JsonUtils.parseObject("{\"sex\":\"M\",\"username\":\"hualuomoli\",\"nickname\":\"花落莫离\",\"age\":20}", GetObjectdataResultJsonEntityUser.class);
		return obj;
	}
	public
	void
	getNodata(GetNodataEntity getNodataEntity) {
		// TODO
	}
	public
	Page
	getPagedata(GetPagedataEntity getPagedataEntity) {
		// TODO
		java.util.List<GetPagedataResultJsonEntityUser> list = JsonUtils.parseList("[{\"sex\":\"M\",\"username\":\"hualuomoli\",\"nickname\":\"花落莫离\",\"age\":20}]", GetPagedataResultJsonEntityUser.class);
		Page page = new Page();
		page.setPageNo(3);
		page.setPageSize(10);
		page.setCount(100);
		page.setDataList(list);
		return page;
	}
	public
	java.util.List<GetListdataResultJsonEntityUser>
	getListdata(GetListdataEntity getListdataEntity) {
		// TODO
		java.util.List<GetListdataResultJsonEntityUser> list = JsonUtils.parseList("[{\"sex\":\"M\",\"username\":\"hualuomoli\",\"nickname\":\"花落莫离\",\"age\":20}]", GetListdataResultJsonEntityUser.class);
		return list;
	}

}
