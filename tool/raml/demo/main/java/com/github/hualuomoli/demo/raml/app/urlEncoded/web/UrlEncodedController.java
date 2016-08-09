package com.github.hualuomoli.demo.raml.app.urlEncoded.web;

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
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.service.UrlEncodedService;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description 表单提交
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/urlEncoded")
@RestController(value = "com.github.hualuomoli.demo.raml.app.urlEncoded.UrlEncodedController")
public class UrlEncodedController {
	
	@Autowired
	private UrlEncodedService urlEncodedService;
	
	/**
	 * URI提交
	 * @param id ID
	 */
	@RequestMapping(value = "/uriparam/{id}", method = RequestMethod.POST, produces = { "application/json" })
	public String postUriparamById(
	@PathVariable(value = "id")
	String id,
	PostUriparamByIdEntity postUriparamByIdEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postUriparamByIdEntity.setId(id);
		
		urlEncodedService.postUriparamById(postUriparamByIdEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * 
	 * @param pageSize 每页显示数据个数
	 * @param pageNumber 分页编码
	 */
	@RequestMapping(value = "/uriformparam/{pageNumber}/{pageSize}", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String postUriformparamByPageNumberPageSize(
	@PathVariable(value = "pageSize")
	Integer pageSize,
	@PathVariable(value = "pageNumber")
	Integer pageNumber,
	PostUriformparamByPageNumberPageSizeUrlEncodedEntity postUriformparamByPageNumberPageSizeUrlEncodedEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postUriformparamByPageNumberPageSizeUrlEncodedEntity.setPageSize(pageSize);
		postUriformparamByPageNumberPageSizeUrlEncodedEntity.setPageNumber(pageNumber);
		
		urlEncodedService.postUriformparamByPageNumberPageSize(postUriformparamByPageNumberPageSizeUrlEncodedEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/formparam", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String postFormparam(
	PostFormparamUrlEncodedEntity postFormparamUrlEncodedEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		urlEncodedService.postFormparam(postFormparamUrlEncodedEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * 提交
	 */
	@RequestMapping(value = "/noparam", method = RequestMethod.POST, produces = { "application/json" })
	public String postNoparam(
	PostNoparamEntity postNoparamEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		urlEncodedService.postNoparam(postNoparamEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
