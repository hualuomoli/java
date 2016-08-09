package com.github.hualuomoli.demo.raml.app.delete.web;

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
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteNoparamEntity;
import com.github.hualuomoli.demo.raml.app.delete.entity.DeleteNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.delete.service.DeleteService;
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description 删除
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RequestVersion(value = "1.0")
@RequestMapping(value = "/app/delete")
@RestController(value = "com.github.hualuomoli.demo.raml.app.delete.DeleteController")
public class DeleteController {
	
	@Autowired
	private DeleteService deleteService;
	
	/**
	 * 
	 * @param id ID
	 */
	@RequestMapping(value = "/uriparam/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public String deleteUriparamById(
	@PathVariable(value = "id")
	String id,
	DeleteUriparamByIdEntity deleteUriparamByIdEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		deleteUriparamByIdEntity.setId(id);
		
		deleteService.deleteUriparamById(deleteUriparamByIdEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/noparam", method = RequestMethod.DELETE, produces = { "application/json" })
	public String deleteNoparam(
	DeleteNoparamEntity deleteNoparamEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		deleteService.deleteNoparam(deleteNoparamEntity);
		return AppRestResponse.getNoData();
		
	}
	
}
