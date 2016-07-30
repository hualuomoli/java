package com.github.hualuomoli;

import java.util.Date;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	public static final String TOKEN_NAME = "token";

	/**
	 * 获取登录用户名
	 * @return 登录用户名
	 */
	String getUsername();

	/**
	 * 设置用户
	 * @param key 键
	 * @param username 用户名
	 */
	void setUsername(String key, String username);

	/**
	 * 刷新
	 */
	void refresh();

	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	Date getCurrentDate();

}
