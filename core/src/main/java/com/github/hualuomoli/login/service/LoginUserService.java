package com.github.hualuomoli.login.service;

import java.util.Date;
import java.util.HashSet;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	// 获取登录用户名,如果用户未登录,使用system
	String getUsername();

	// 获取登录的用户名,如果用户未登录,抛出异常
	String getLoginUsername();

	// 登录用户的角色
	HashSet<String> getLoginUserRoles();

	// 登录用户的权限
	HashSet<String> getLoginUserPermissions();

	// 设置用户
	void setUsername(String token, String username);

	// 刷新
	void refreshUsername();

	// 获取当前时间
	Date getCurrentDate();

}
