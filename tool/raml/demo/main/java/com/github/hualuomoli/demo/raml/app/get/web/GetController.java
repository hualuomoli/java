package com.github.hualuomoli.demo.raml.app.get.web;

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

import com.github.hualuomoli.mvc.rest.AppRestResponse;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.service.GetService;

/**
 * @Description get方式请求
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RestController(value = "com.github.hualuomoli.demo.raml.app.get.GetController")
@RequestMapping(value = "/app/get")
public class GetController {
	
	@Autowired
	private GetService getService;
	
	/**
	 * @param id ID
	 */
	@RequestMapping(value = "/uriparam/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public String getUriparamById(
	@PathVariable(value = "id")
	String id,
	GetUriparamByIdEntity getUriparamByIdEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		getUriparamByIdEntity.setId(id);
		
		getService.getUriparamById(getUriparamByIdEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 */
	@RequestMapping(value = "/queryparam", method = RequestMethod.GET, produces = { "application/json" })
	public String getQueryparam(
	GetQueryparamEntity getQueryparamEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		getService.getQueryparam(getQueryparamEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * @param pageSize 每页数据个数
	 * @param pageNumber 当前页码
	 */
	@RequestMapping(value = "/uriqueryparam/{pageNumber}/{pageSize}", method = RequestMethod.GET, produces = { "application/json" })
	public String getUriqueryparamByPageNumberPageSize(
	@PathVariable(value = "pageSize")
	Integer pageSize,
	@PathVariable(value = "pageNumber")
	Integer pageNumber,
	GetUriqueryparamByPageNumberPageSizeEntity getUriqueryparamByPageNumberPageSizeEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		getUriqueryparamByPageNumberPageSizeEntity.setPageSize(pageSize);
		getUriqueryparamByPageNumberPageSizeEntity.setPageNumber(pageNumber);
		
		getService.getUriqueryparamByPageNumberPageSize(getUriqueryparamByPageNumberPageSizeEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 */
	@RequestMapping(value = "/noparam", method = RequestMethod.GET, produces = { "application/json" })
	public String getNoparam(
	GetNoparamEntity getNoparamEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		getService.getNoparam(getNoparamEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
