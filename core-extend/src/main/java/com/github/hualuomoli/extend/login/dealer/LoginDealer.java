package com.github.hualuomoli.extend.login.dealer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.hualuomoli.extend.base.entity.BaseUser;

/**
 * 登录处理器
 * @author hualuomoli
 *
 */
public interface LoginDealer {

	// 是否支持
	boolean support(HttpServletRequest request);

	// 用户不存在
	String notFound(HttpServletRequest request, HttpServletResponse response);

	// 用户名或密码错误
	String invalidUsernameOrPassword(HttpServletRequest request, HttpServletResponse response);

	// 用户状态非正常
	String notNomalStatus(BaseUser baseUser, HttpServletRequest request, HttpServletResponse response);

	// 登录成功处理
	String success(BaseUser baseUser, HttpServletRequest request, HttpServletResponse response);

}
