package com.github.hualuomoli.demo.raml.app.multipart.web;

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
import com.github.hualuomoli.demo.raml.app.multipart.entity.PostFileByIdFileEntity;
import com.github.hualuomoli.demo.raml.app.multipart.entity.PostFileByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.multipart.service.MultipartService;

/**
 * @Description 文件上传
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:46
 * @Version 1.0
 */
@RestController(value = "com.github.hualuomoli.demo.raml.app.multipart.MultipartController")
@RequestMapping(value = "/app/multipart")
public class MultipartController {
	
	@Autowired
	private MultipartService multipartService;
	
	/**
	 * @param id ID
	 * @param photo 头像
	 */
	@RequestMapping(value = "/file/{id}", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
	public String postFileById(
	@PathVariable(value = "id")
	String id,
	@RequestParam(value = "photo", required = true)
	MultipartFile photo,
	PostFileByIdFileEntity postFileByIdFileEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postFileByIdFileEntity.setId(id);
		postFileByIdFileEntity.setPhoto(photo);
		
		multipartService.postFileById(postFileByIdFileEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
