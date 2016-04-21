package com.github.hualuomoli.mvc.security;

/**
 * 权限
 * @author hualuomoli
 *
 */
public interface Auth {

	public static final int DEFAULT_EXPIRE = 30 * 60; // 30 分钟

	/**
	 * 获取数据有效期,单位为秒
	 * @return 数据保存有效期
	 */
	int getDataExpire();

	/**
	 * 登录
	 * @param username 用户名
	 * @return token
	 */
	String login(String username);

	/**
	 * 登录
	 * @param username 用户名
	 * @param token token(需要保证唯一)
	 */
	void login(String username, String token);

	/**
	 * 登出
	 * @param token 用户token
	 */
	void logout(String token);

	/**
	 * 用户是否登录
	 * @param token 用户token
	 * @return 如果用户已经登录,返回true
	 */
	boolean isLogin(String token);

	/**
	 * 已经登录的用户(如允许多终端登录)
	 * @param username 用户名
	 * @return 已经登录的用户token
	 */
	String[] logged(String username);

	/**
	 * 用户是否具有该角色
	 * @param username 用户名
	 * @param role 角色名称
	 * @return 如果用户具有角色,返回true
	 */
	boolean hasRole(String username, String role);

	/**
	 * 用户是否具有该权限
	 * @param username 用户名
	 * @param permission 权限名称
	 * @return 如果用户具有权限,返回true
	 */
	boolean hasPermission(String username, String permission);

}
