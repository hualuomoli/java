package com.github.hualuomoli.extend.login.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.exception.CodeException;
import com.github.hualuomoli.extend.base.entity.BaseUser;
import com.github.hualuomoli.extend.constant.Code;
import com.github.hualuomoli.extend.constant.DataRegex;
import com.github.hualuomoli.extend.login.dealer.LoginDealer;
import com.github.hualuomoli.extend.login.web.LoginController.LoginUser;
import com.github.hualuomoli.extend.rest.AppRestResponse;
import com.github.hualuomoli.extend.user.service.UserService;
import com.github.hualuomoli.login.service.LoginUserService;
import com.google.common.collect.Lists;

@Service(value = "com.github.hualuomoli.extend.login.service.LoginService")
@Transactional(readOnly = true)
public class LoginService implements ApplicationContextAware {

	private List<LoginDealer> dealerList = Lists.newArrayList();

	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private UserService userService;

	public String login(LoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {

		// 处理者
		LoginDealer dealer = this.getLoginDealer(request);

		// 获取登录用户
		BaseUser baseUser = null;
		String username = loginUser.getUsername();
		if (username.matches(DataRegex.PHONE)) {
			// 手机号码
			baseUser = userService.getByPhone(username);
		} else if (username.matches(DataRegex.EMAIL)) {
			// 邮箱
			baseUser = userService.getByEmail(username);
		} else {
			// 用户名
			baseUser = userService.getByUsername(username);
		}

		// 用户未找到
		if (baseUser == null) {
			return dealer.notFound(request, response);
		}
		// 密码错误
		if (!StringUtils.equals(loginUser.getPassword(), baseUser.getPassword())
				&& !userService.checkPassword(loginUser.getPassword(), baseUser.getPassword())) {
			return dealer.invalidUsernameOrPassword(request, response);
		}
		// 非正常用户
		if (baseUser.getStatus() != Status.NOMAL.getValue()) {
			return dealer.notNomalStatus(baseUser, request, response);
		}
		// 登录成功
		return dealer.success(baseUser, request, response);
	}

	// 获取处理者
	private LoginDealer getLoginDealer(HttpServletRequest request) {
		for (LoginDealer loginDealer : dealerList) {
			if (loginDealer.support(request)) {
				return loginDealer;
			}
		}
		return new DefalutLoginDealer(loginUserService);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, LoginDealer> map = applicationContext.getBeansOfType(LoginDealer.class);
		if (map == null || map.size() == 0) {
			return;
		}
		for (LoginDealer loginDealer : map.values()) {
			dealerList.add(loginDealer);
		}
	}

	// 默认实现
	public static class DefalutLoginDealer implements LoginDealer {

		private LoginUserService loginUserService;

		public DefalutLoginDealer(LoginUserService loginUserService) {
			this.loginUserService = loginUserService;
		}

		@Override
		public boolean support(HttpServletRequest request) {
			return true;
		}

		@Override
		public String notFound(HttpServletRequest request, HttpServletResponse response) {
			throw new CodeException(Code.USER_NOT_FOUND);
		}

		@Override
		public String invalidUsernameOrPassword(HttpServletRequest request, HttpServletResponse response) {
			throw new CodeException(Code.USER_INVALID_USERNAME_OR_PASSWORD);
		}

		public String notNomalStatus(BaseUser baseUser, HttpServletRequest request, HttpServletResponse response) {
			throw new CodeException(Code.USER_NOT_NOMAL);
		}

		@Override
		public String success(BaseUser BaseUser, HttpServletRequest request, HttpServletResponse response) {

			// 获取token
			String token = RandomUtils.getUUID();
			// 用户
			String username = BaseUser.getUsername();
			// 添加到缓存
			loginUserService.login(token, username);

			// 添加到token，cookie
			response.addCookie(new Cookie("token", token));
			response.addHeader("token", token);

			// 返回
			TokenMessage tokenMessage = new TokenMessage();
			tokenMessage.setToken(token);

			return AppRestResponse.getObjectData("data", tokenMessage);
		}

		public static class TokenMessage {
			private String token;

			public String getToken() {
				return token;
			}

			public void setToken(String token) {
				this.token = token;
			}
		}

	}

}
