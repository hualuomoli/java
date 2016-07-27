package com.github.hualuomoli.demo.raml.app.ret.web;

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
import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.service.RetService;

/**
 * @Description 响应
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@RestController(value = "com.github.hualuomoli.demo.raml.app.ret.RetController")
@RequestMapping(value = "/app/ret")
public class RetController {
	
	@Autowired
	private RetService retService;
	
	/**
	 * 
	 */
	@RequestMapping(value = "/objectdata", method = RequestMethod.GET, produces = { "application/json" })
	public String getObjectdata(
	GetObjectdataEntity getObjectdataEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		GetObjectdataResultJsonEntityUser getObjectdataResultJsonEntityUser = retService.getObjectdata(getObjectdataEntity);
		return AppRestResponse.getObjectData("user", getObjectdataResultJsonEntityUser);
		
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/nodata", method = RequestMethod.GET, produces = { "application/json" })
	public String getNodata(
	GetNodataEntity getNodataEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		retService.getNodata(getNodataEntity);
		return AppRestResponse.getNoData();
		
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/pagedata", method = RequestMethod.GET, produces = { "application/json" })
	public String getPagedata(
	GetPagedataEntity getPagedataEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		Page page = retService.getPagedata(getPagedataEntity);
		return AppRestResponse.getPageData("users", page);
		
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/listdata", method = RequestMethod.GET, produces = { "application/json" })
	public String getListdata(
	GetListdataEntity getListdataEntity,
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		
		java.util.List<GetListdataResultJsonEntityUser> list = retService.getListdata(getListdataEntity);
		return AppRestResponse.getListData("users", list);
		
	}
	
}
