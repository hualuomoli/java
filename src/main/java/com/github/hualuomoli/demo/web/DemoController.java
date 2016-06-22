package com.github.hualuomoli.demo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.hualuomoli.demo.entity.Demo;
import com.github.hualuomoli.demo.service.DemoService;

@Controller(value = "com.github.hualuomoli.demo.web.DemoController")
@RequestMapping(value = "/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping(value = "/{id}")
	@ResponseBody
	public String get(@PathVariable(value = "id") String id, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(id);
		Demo demo = demoService.get(id);
		return demo.toString();
	}

	@RequestMapping(value = "")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "list";
	}

}
