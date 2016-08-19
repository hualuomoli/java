package com.github.hualuomoli.demo.raml.app.origin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetListEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetListResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.origin.service.OriginService;
import com.github.hualuomoli.mvc.annotation.RequestPermission;
import com.github.hualuomoli.mvc.annotation.RequestRole;
import com.github.hualuomoli.mvc.annotation.RequestToken;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description 包括省市区、养殖类型、养殖种类等基础数据
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/origin")
@RestController(value = "com.github.hualuomoli.demo.raml.app.origin.OriginController")
public class OriginController {
	
	@Autowired
	private OriginService originService;
	
	/**
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = { "application/json" })
	public String getList(
	GetListEntity getListEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		java.util.List<GetListResultJsonEntity> list = originService.getList(getListEntity);
		return AppRestResponse.toJson(list);
		
	}
	/**
	 * get
	 */
	@RequestMapping(value = "/object", method = RequestMethod.GET, produces = { "application/json" })
	public String getObject(
	GetObjectEntity getObjectEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		GetObjectResultJsonEntity getObjectResultJsonEntity = originService.getObject(getObjectEntity);
		return AppRestResponse.toJson(getObjectResultJsonEntity);
		
	}
	
}
