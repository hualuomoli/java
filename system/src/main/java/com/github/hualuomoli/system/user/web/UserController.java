package com.github.hualuomoli.system.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.mvc.annotation.RequestToken;
import com.github.hualuomoli.mvc.annotation.RequestVersion;
import com.github.hualuomoli.system.user.service.UserService;

@RequestVersion
@RequestMapping(value = "/system/user")
@RestController(value = "com.github.hualuomoli.system.user.web.UserController")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 查询用户的角色
	 */
	@RequestMapping(value = "/role", method = RequestMethod.GET, produces = { "applicaiton/json" })
	public String getRole() {

		List<BaseRole> list = userService.getRole();

		return AppRestResponse.getListData("roles", list);
	}

	/**
	 * 查询用户的菜单
	 */
	@RequestToken
	@RequestMapping(value = "/menu", method = RequestMethod.GET, produces = { "applicaiton/json" })
	public String getMenu() {

		List<BaseMenu> list = userService.getMenu();

		return AppRestResponse.getListData("menus", list);

	}

}
