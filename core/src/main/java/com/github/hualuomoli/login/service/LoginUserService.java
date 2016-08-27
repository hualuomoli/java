package com.github.hualuomoli.login.service;

import java.util.Date;
import java.util.HashSet;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	// 获取登录用户名,如果未登录用户操作,请手动设置
	String getUsername();

	// 登录用户的角色
	HashSet<String> getUserRoles();

	// 登录用户的权限
	HashSet<String> getUserPermissions();

	// 登录(设置用户)
	void login(String token, String username);

	// 登出(移除token)
	void logout();

	// 刷新
	void refresh();

	// 获取当前时间
	Date getCurrentDate();

}
