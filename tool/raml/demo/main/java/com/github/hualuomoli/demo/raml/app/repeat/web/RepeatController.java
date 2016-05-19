package com.github.hualuomoli.demo.raml.app.repeat.web;

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
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileFileEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.service.RepeatService;

/**
 * @Description 重复值
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RestController(value = "com.github.hualuomoli.demo.raml.app.repeat.RepeatController")
@RequestMapping(value = "/app/repeat")
public class RepeatController {
	
	@Autowired
	private RepeatService repeatService;
	
	/**
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public String postJson(
	@RequestBody 
	PostJsonJsonEntity postJsonJsonEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		PostJsonResultJsonEntityUser postJsonResultJsonEntityUser = repeatService.postJson(postJsonJsonEntity);
		return AppRestResponse.getObjectData("user", postJsonResultJsonEntityUser);
		
	}
	/**
	 */
	@RequestMapping(value = "/urlencoded", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String postUrlencoded(
	PostUrlencodedUrlEncodedEntity postUrlencodedUrlEncodedEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		repeatService.postUrlencoded(postUrlencodedUrlEncodedEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * @param photo 头像
	 */
	@RequestMapping(value = "/file", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
	public String postFile(
	@RequestParam(value = "photo", required = true)
	MultipartFile[] photo,
	PostFileFileEntity postFileFileEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postFileFileEntity.setPhoto(photo);
		
		repeatService.postFile(postFileFileEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
