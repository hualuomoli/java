package com.github.hualuomoli.extend.login.dealer;

import javax.servlet.http.HttpServletRequest;

import com.github.hualuomoli.extend.base.entity.BaseUser;
import com.github.hualuomoli.extend.login.LoginController.LoginUser;

/**
 * 登录处理器
 * @author hualuomoli
 *
 */
public interface LoginDealer {

	// 是否支持
	boolean support(HttpServletRequest request);

	// 用户不存在
	String error();

	// 用户名或密码错误
	String error(LoginUser loginUser);

	// 登录成功处理
	String success(BaseUser baseUser, HttpServletRequest request);

}
