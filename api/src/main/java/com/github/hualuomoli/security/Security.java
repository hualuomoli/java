package com.github.hualuomoli.security;

import java.util.Set;

/**
 * MVC 安全
 * @author hualuomoli
 *
 */
public interface Security {

	public static final String USERNAME = "username";
	public static final String TOKEN = "token";

	/**
	 * 登录
	 * @param username 登录用户
	 * @return 唯一token
	 */
	String login(String username);

	/**
	 * 是否已经登录
	 * @param token 唯一token
	 * @return 如果token未空，未登录，如果token不存在，则token过期
	 */
	boolean isLogin(String token);

	/**
	 * 是否登录
	 * @param username 用户名
	 * @param token 唯一token
	 * @return 如果username和token匹配，登录成功，否则token过期
	 */
	boolean isLogin(String username, String token);

	/**
	 * 登出
	 * @param username 用户名
	 */
	void logout(String token);

	/**
	 * 设置用户拥有的角色
	 * @param username 用户名
	 * @param roles 角色集合
	 */
	void addRole(String username, Set<String> roles);

	/**
	 * 是否具有该角色
	 * @param username 用户名
	 * @param role 角色
	 * @return 如果拥有该角色，返回true，否则返回false
	 */
	boolean hasRole(String username, String role);

	/**
	 * 设置用户拥有的权限
	 * @param username 用户名
	 * @param permissions 权限集合
	 */
	void addPermisions(String username, Set<String> permissions);

	/**
	 * 是否具有该权限
	 * @param username 用户名
	 * @param permission 权限
	 * @return 如果拥有该权限，返回true，否则返回false
	 */
	boolean hasPermission(String username, String permission);

}
