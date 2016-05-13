package com.github.hualuomoli.demo.raml.app.def.web;

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
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostByIdFileEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostjsonByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.entity.PostPostjsonByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.def.service.DefService;

/**
 * @Description 默认值
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:02
 * @Version 1.0
 */
@RestController(value = "com.github.hualuomoli.demo.raml.app.def.DefController")
@RequestMapping(value = "/app/def")
public class DefController {
	
	@Autowired
	private DefService defService;
	
	/**
	 * @param id ID
	 * @param photo 头像
	 */
	@RequestMapping(value = "/post/{id}", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
	public String postPostById(
	@PathVariable(value = "id")
	String id,
	@RequestParam(value = "photo", required = true)
	MultipartFile photo,
	PostPostByIdFileEntity postPostByIdFileEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postPostByIdFileEntity.setId(id);
		postPostByIdFileEntity.setPhoto(photo);
		
		defService.postPostById(postPostByIdFileEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * @param id ID
	 */
	@RequestMapping(value = "/postjson/{id}", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public String postPostjsonById(
	@PathVariable(value = "id")
	String id,
	@RequestBody 
	PostPostjsonByIdJsonEntity postPostjsonByIdJsonEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postPostjsonByIdJsonEntity.setId(id);
		
		defService.postPostjsonById(postPostjsonByIdJsonEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
