package com.github.hualuomoli.mvc.security;

import com.github.hualuomoli.mvc.security.entity.User;
import com.github.hualuomoli.mvc.security.exception.AuthException;

/**
 * 验证权限,只验证权限,对用户的密码不校验
 * @author hualuomoli
 *
 */
public interface Auth {

	public static final String ERROR_CODE_INVALID = "40101"; // 用户名或密码错误(登录)
	public static final String ERROR_CODE_NO_LOGIN = "40102"; // 未登录
	public static final String ERROR_CODE_OVERTIME = "40103"; // 登录超时

	// 登陆
	User login(User user) throws AuthException;

	// 登出
	void logout(User user) throws AuthException;

	// 是否已经登陆
	boolean isLogin(User user) throws AuthException;

	// 是否有角色
	boolean hasRole(User user, String role) throws AuthException;

	// 是否有权限
	boolean hasPermission(User user, String permission) throws AuthException;

}
