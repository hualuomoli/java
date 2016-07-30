package com.github.hualuomoli;

import java.util.Date;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	public static final String TOKEN_NAME = "token";

	// 获取登录用户名
	String getUsername();

	// 获取登录用户信息Object
	<T> T getObject();

	// 设置用户
	void setUsername(String union, String username);

	// 设置用户
	<T> void setObject(String username, T t);

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
