package com.github.hualuomoli.extend.logout.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.login.service.LoginUserService;

/**
 * 登出
 * @author hualuomoli
 *
 */
@RequestMapping(value = "/logout")
@RestController(value = "com.github.hualuomoli.extend.logout.web.LogoutController")
public class LogoutController {

	@Autowired
	private LoginUserService loginUserService;

	@RequestMapping(value = "", produces = { "application/json" })
	public String logout() {
		loginUserService.removeToken();
		return AppRestResponse.getNoData();
	}

}
