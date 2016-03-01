package com.github.hualuomoli.web.login.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.base.shiro.ShiroUtils;
import com.github.hualuomoli.commons.json.JsonMapper;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Value(value = "${shiro.authcPath}")
	private String authcPath;

	// login page
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login() {
		return "/login";
	}

	// do login
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (StringUtils.equals(userName, "admin") && StringUtils.equals(password, "admin")) {
			ShiroUtils.login(userName, password);
			return "redirect:" + authcPath + "/index";
		}
		model.addAttribute("message", "invalidate userName or password");
		return "/login";
	}

	// do login asynchronous
	@RequestMapping(value = "check")
	@ResponseBody
	public String doAsynchronousLogin(HttpServletRequest request, HttpServletResponse response, Model model) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		Map<String, String> ret = Maps.newHashMap();

		if (StringUtils.equals(userName, "admin") && StringUtils.equals(password, "admin")) {
			ret.put("ok", "ok");
			ret.put("message", "login sucess");
			ret.put("indexPath", authcPath + "/index");
		} else {
			ret.put("ok", "error");
			ret.put("message", "invalidate userName or password");
		}
		return JsonMapper.toJsonString(ret);
	}

	// other url
	@RequestMapping(value = "*")
	public String nomatch() {
		return "login";
	}

}
