package ${packageName}.api.${moduleName?lower_case}.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "${packageName}.api.${moduleName?lower_case}.web.${className}Controller")
@RequestMapping(value = "/api/${modulePath}")
public class ${className}Controller {

	<#list resourceList as resource>
	<#list resource.actionList as action>
	@RequestMapping(value = "${resource.methodUriPath}", method = RequestMethod.${action.type})
	@ResponseBody
	public String ${resource.methodName}(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(request.getParameterMap());
		return "${action.example}";
	}
	</#list>
	</#list>
	
}
