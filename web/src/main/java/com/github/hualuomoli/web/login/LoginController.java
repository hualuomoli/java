package com.github.hualuomoli.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.commons.json.JsonMapper;
import com.github.hualuomoli.web.ret.MessageReturn;
import com.github.hualuomoli.web.ret.web.ErrorWebMessageReturn;
import com.github.hualuomoli.web.ret.web.SuccessWebMessageReturn;
import com.github.hualuomoli.web.shiro.util.ShiroUtils;

@Controller
@RequestMapping(value = "")
public class LoginController {

	// private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// login
	@RequestMapping(value = "login", method = { RequestMethod.GET })
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "please use post method";
	}

	// login
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	@ResponseBody
	public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model) {

		// sleep 1 seconds
		// Run.sleep(1);

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		MessageReturn messageReturn;

		if (StringUtils.equals(username, "admin") && StringUtils.equals(password, "admin")) {
			ShiroUtils.login(username, password);
			messageReturn = new SuccessWebMessageReturn();
		} else {
			messageReturn = new ErrorWebMessageReturn("invalid username or password");
		}

		return JsonMapper.toJsonString(messageReturn);
	}

	// login
	@RequestMapping(value = "logout", method = { RequestMethod.POST })
	@ResponseBody
	public String doLogout(HttpServletRequest request, HttpServletResponse response, Model model) {

		ShiroUtils.logout();

		MessageReturn messageReturn = new SuccessWebMessageReturn();

		return JsonMapper.toJsonString(messageReturn);
	}

}
