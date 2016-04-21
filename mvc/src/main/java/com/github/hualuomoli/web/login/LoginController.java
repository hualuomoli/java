package com.github.hualuomoli.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.commons.servlet.TokenUtils;
import com.github.hualuomoli.mvc.security.Auth;
import com.github.hualuomoli.mvc.security.exception.MvcException;

/**
 * 登录,登出
 * @author hualuomoli
 *
 */
@Controller(value = "com.github.hualuomoli.web.login.LoginController")
@RequestMapping(value = "")
public class LoginController {

	@Autowired
	private Auth auth;

	@RequestMapping(value = "login")
	@ResponseBody
	public void login(@RequestBody Entity entity, HttpServletRequest request, HttpServletResponse response) {
		// TODO
		if (!StringUtils.equals(entity.getUsername(), "admin") || !StringUtils.equals(entity.getPassword(), "admin")) {
			throw new MvcException(MvcException.ERROR_USER_INVALID, "用户名或密码错误");
		}
		// login to auth
		String token = auth.login(entity.getUsername());
		TokenUtils.setToken(response, token);
	}

	@RequestMapping(value = "logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		auth.logout(TokenUtils.getToken(request));
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
