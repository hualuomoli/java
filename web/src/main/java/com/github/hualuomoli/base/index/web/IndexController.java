package com.github.hualuomoli.base.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${shiro.authcPath}/index")
public class IndexController {

	@RequestMapping(value = "")
	public String index() {
		return "index";
	}

}
