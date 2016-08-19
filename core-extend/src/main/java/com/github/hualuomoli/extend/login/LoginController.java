package com.github.hualuomoli.extend.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hualuomoli.extend.login.service.LoginService;
import com.github.hualuomoli.mvc.validator.EntityValidator;

@RequestMapping(value = "/login")
@RestController(value = "com.github.hualuomoli.extend.login.LoginController")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 登录,登录用户可以是用户名/邮箱/手机号码
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/x-www-form-urlencoded" }, produces = { "application/json" })
	public String login(LoginUser loginUser, HttpServletRequest request) {
		return loginService.login(loginUser, request);
	}

	public static class LoginUser implements EntityValidator {

		private String username;
		private String password;

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
