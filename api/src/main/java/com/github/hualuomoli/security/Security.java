package com.github.hualuomoli.security;

import java.util.Set;

import com.github.hualuomoli.security.entity.User;

/**
 * MVC 安全
 * @author hualuomoli
 *
 */
public interface Security {

	/**
	 * 登录
	 * @param user 用户
	 * @return 登录成功返回用户token,否则返回null
	 */
	boolean login(User user);

	/**
	 * 用户是否已经登录
	 * @param token 用户token
	 * @return 用户是否已经登录
	 */
	boolean isLogin(String token);

	/**
	 * 登出
	 * @param token 用户token
	 */
	void logout(String token);

	/**
	 * 获取用户拥有的角色
	 * @param username 用户名
	 * @return 角色集合
	 */
	Set<String> getRoles(String username);

	/**
	 * 用户是否具有角色
	 * @param username 用户名
	 * @param role 角色
	 * @return 如果用户有该角色，返回true,否则返回false
	 */
	boolean hasRole(String username, String role);

	/**
	 * 获取用户权限
	 * @param username 用户名
	 * @return 用户权限
	 */
	Set<String> getPermissions(String username);

	/**
	 * 用户是否具有该权限
	 * @param username 用户名
	 * @param permission 权限
	 * @return 如果用户有该全新,返回true,否则返回false
	 */
	boolean hasPermission(String username, String permission);

}
