package com.github.hualuomoli;

import java.util.Date;

/**
 * 登录用户service
 * @author hualuomoli
 *
 */
public interface LoginUserService {

	// 获取登录用户名
	String getUsername();

	// 设置用户
	void setUsername(String token, String username);

	// 刷新
	void refreshUsername();

	// 获取当前时间
	Date getCurrentDate();

}
