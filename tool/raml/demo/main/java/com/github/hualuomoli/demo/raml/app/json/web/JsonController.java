package com.github.hualuomoli.demo.raml.app.json.web;

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
import com.github.hualuomoli.demo.raml.app.json.entity.PostUserByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.json.entity.PostUserByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.json.service.JsonService;
import com.github.hualuomoli.mvc.annotation.RequestPermission;
import com.github.hualuomoli.mvc.annotation.RequestRole;
import com.github.hualuomoli.mvc.annotation.RequestToken;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description JSON数据
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/json")
@RestController(value = "com.github.hualuomoli.demo.raml.app.json.JsonController")
public class JsonController {
	
	@Autowired
	private JsonService jsonService;
	
	/**
	 * update
	 * @param id ID
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public String postUserById(
	@PathVariable(value = "id")
	String id,
	@RequestBody 
	PostUserByIdJsonEntity postUserByIdJsonEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postUserByIdJsonEntity.setId(id);
		
		jsonService.postUserById(postUserByIdJsonEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
