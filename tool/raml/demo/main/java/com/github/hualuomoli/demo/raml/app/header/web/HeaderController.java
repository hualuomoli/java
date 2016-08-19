package com.github.hualuomoli.demo.raml.app.header.web;

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
import com.github.hualuomoli.demo.raml.app.header.entity.GetGetEntity;
import com.github.hualuomoli.demo.raml.app.header.entity.GetGetResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.header.service.HeaderService;
import com.github.hualuomoli.mvc.annotation.RequestPermission;
import com.github.hualuomoli.mvc.annotation.RequestRole;
import com.github.hualuomoli.mvc.annotation.RequestToken;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description 增加header验证
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/header")
@RestController(value = "com.github.hualuomoli.demo.raml.app.header.HeaderController")
public class HeaderController {
	
	@Autowired
	private HeaderService headerService;
	
	/**
	 * 查询
	 */
	@RequestToken
	@RequestRole(value = {"system", "app"})
	@RequestPermission(value = "system:menu:view")
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = { "application/json" })
	public String getGet(
	GetGetEntity getGetEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		headerService.getGet(getGetEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
