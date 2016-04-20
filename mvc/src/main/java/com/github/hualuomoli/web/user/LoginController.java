package com.github.hualuomoli.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.mvc.interceptor.AuthInterceptor;
import com.github.hualuomoli.mvc.security.Auth;
import com.github.hualuomoli.mvc.security.entity.User;
import com.github.hualuomoli.mvc.security.exception.AuthException;

/**
 * 登录,登出
 * @author hualuomoli
 *
 */
@Controller(value = "com.github.hualuomoli.web.user.LoginController")
@RequestMapping(value = "")
public class LoginController {

	@Autowired
	private Auth auth;

	@RequestMapping(value = "login")
	@ResponseBody
	public User login(Entity entity, HttpServletRequest request, HttpServletResponse response) {
		// TODO
		if (!StringUtils.equals(entity.getUsername(), "admin") || !StringUtils.equals(entity.getPassword(), "admin")) {
			throw new AuthException(Auth.ERROR_CODE_INVALID, "用户名或密码错误");
		}
		// login to auth
		User user = AuthInterceptor.getUser(request);
		user.setUsername(entity.getUsername());
		user = auth.login(user);
		// add token to header
		AuthInterceptor.setToken(response, user.getToken());

		return user;
	}

	@RequestMapping(value = "logout")
	@ResponseBody
	public void logout(Entity entity, HttpServletRequest request, HttpServletResponse response) {
		User user = AuthInterceptor.getUser(request);
		user.setUsername(entity.getUsername());
		auth.logout(user);
	}

	// user
	public static class Entity {
		private String username;
		private String password;

		public Entity() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
