package com.github.hualuomoli.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.commons.json.JsonMapper;
import com.github.hualuomoli.ret.MessageReturn;
import com.github.hualuomoli.ret.login.LoginMessageReturn;
import com.github.hualuomoli.ret.none.NoDataMessageReturn;
import com.github.hualuomoli.security.Security;

@Controller
@RequestMapping(value = "")
public class LoginController {

	@Autowired
	private Security security;

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
	public String doLogin(Login login, HttpServletRequest request, HttpServletResponse response, Model model) {

		MessageReturn messageReturn;

		if (StringUtils.equals(login.getUsername(), "admin") && StringUtils.equals(login.getPassword(), "admin")) {
			String token = security.login(login.getUsername());
			messageReturn = new LoginMessageReturn(NoDataMessageReturn.CODE_SUCCESS, "", token);
		} else {
			messageReturn = new NoDataMessageReturn("50001", "invalid username or password");
		}

		return JsonMapper.toJsonString(messageReturn);
	}

	// login
	@RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String doLogout(HttpServletRequest request, HttpServletResponse response, Model model) {

		security.logout(request.getParameter(Security.TOKEN));

		MessageReturn messageReturn = new NoDataMessageReturn(NoDataMessageReturn.CODE_SUCCESS);

		return JsonMapper.toJsonString(messageReturn);
	}

}
