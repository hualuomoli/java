package com.github.hualuomoli.demo.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.commons.servlet.TokenUtils;
import com.github.hualuomoli.mvc.security.Auth;
import com.github.hualuomoli.mvc.security.exception.MvcException;
import com.github.hualuomoli.mvc.valid.EntityValidator;

/**
 * 登录,登出
 * @author hualuomoli
 *
 */
@Controller(value = "com.github.hualuomoli.demo.web.login.LoginController")
@RequestMapping(value = "")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private Auth auth;

	/**
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "login", method = { RequestMethod.POST }, consumes = { "application/x-www-form-urlencoded", "application/*" })
	@ResponseBody
	public void login(Entity entity, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.debug("login by urlencoded.");
		if (!this.login(entity)) {
			throw new MvcException(MvcException.ERROR_USER_INVALID, "用户名或密码错误");
		}
		// login to auth
		String token = auth.login(entity.getUsername());
		TokenUtils.setToken(response, token);
	}

	@RequestMapping(value = "login", consumes = { "application/json" })
	@ResponseBody
	public void jsonLogin(@RequestBody Entity entity, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("login by json.");
		if (!this.login(entity)) {
			throw new MvcException(MvcException.ERROR_USER_INVALID, "用户名或密码错误");
		}
		// login to auth
		String token = auth.login(entity.getUsername());
		TokenUtils.setToken(response, token);
	}

	private boolean login(Entity entity) {
		System.out.println(ToStringBuilder.reflectionToString(entity));
		// TODO
		return StringUtils.equals(entity.getUsername(), "admin") && StringUtils.equals(entity.getPassword(), "admin");
	}

	@RequestMapping(value = "logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String token = TokenUtils.getToken(request);
		if (auth.isLogin(token)) { // 如果登录，登出
			auth.logout(TokenUtils.getToken(request));
			TokenUtils.removeToken(response);
		}
	}

	// user
	public static class Entity implements EntityValidator {

		private String username;
		private String password;

		public Entity() {
		}

		@NotEmpty(message = "必填选项")
		@Length(min = 1, max = 5, message = "用户名长度在1-5之间")
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@NotNull(message = "必填选项")
		@NotEmpty(message = "必填选项")
		@Length(min = 1, max = 5, message = "密码长度在1-5之间")
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

}
