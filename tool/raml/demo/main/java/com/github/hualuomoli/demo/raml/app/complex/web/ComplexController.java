package com.github.hualuomoli.demo.raml.app.complex.web;

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
import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.complex.service.ComplexService;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description 复杂json参数
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/complex")
@RestController(value = "com.github.hualuomoli.demo.raml.app.complex.ComplexController")
public class ComplexController {
	
	@Autowired
	private ComplexService complexService;
	
	/**
	 * 提交复杂数据
	 * @param id ID
	 */
	@RequestMapping(value = "/uriform/{id}", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public String postUriformById(
	@PathVariable(value = "id")
	String id,
	@RequestBody 
	PostUriformByIdJsonEntity postUriformByIdJsonEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		postUriformByIdJsonEntity.setId(id);
		
		PostUriformByIdResultJsonEntityUser postUriformByIdResultJsonEntityUser = complexService.postUriformById(postUriformByIdJsonEntity);
		return AppRestResponse.getObjectData("user", postUriformByIdResultJsonEntityUser);
		
	}
	
}
