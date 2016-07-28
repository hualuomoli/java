package ${packageName}.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.JsonUtils;
<#list methods as method>
import ${packageName}.entity.${method.request.className};
import ${packageName}.entity.${method.response.className};
</#list>
import ${packageName}.mapper.${mapperJavaName};


/**
 * @Description ${desc!''}
 * @Author ${author}
 * @Date ${date}
 * @Version ${version}
 */
@Service(value = "${packageName}.${serviceJavaName}")
@Transactional(readOnly = true)
public class ${serviceJavaName} {

	protected static final Logger logger = LoggerFactory.getLogger(${serviceJavaName}.class);

	@Autowired
	protected ${mapperJavaName} ${mapperJavaName?uncap_first};
	
	<#list methods as method>
	<#if method.methodMimeType.method == 'POST' || method.methodMimeType.method == 'PUT' || method.methodMimeType.method == 'DELETE'>
	@Transactional(readOnly = false)
	</#if>
	public
	<#if method.response.resJson.type == 2>
	<#-- no data -->
	void
	<#elseif method.response.resJson.type == 3>
	<#-- object -->
	${method.response.className}
	<#elseif method.response.resJson.type == 4>
	<#-- list -->
	java.util.List<${method.response.className}>
	<#elseif method.response.resJson.type == 5>
	<#-- page -->
	Page
	</#if>
	${method.methodName}(${method.request.className} ${method.request.className?uncap_first}) {
		// TODO
		<#if method.response.resJson.type == 3>
		<#-- object -->
		${method.response.className} obj = JsonUtils.parseObject("${method.response.resJson.exampleData}", ${method.response.className}.class);
		return obj;
		<#elseif method.response.resJson.type == 4>
		<#-- list -->
		java.util.List<${method.response.className}> list = JsonUtils.parseList("${method.response.resJson.exampleData}", ${method.response.className}.class);
		return list;
		<#elseif method.response.resJson.type == 5>
		<#-- page -->
		java.util.List<${method.response.className}> list = JsonUtils.parseList("${method.response.resJson.exampleData}", ${method.response.className}.class);
		Page page = new Page();
		page.setPageNo(3);
		page.setPageSize(10);
		page.setCount(100);
		page.setDataList(list);
		return page;
		</#if>
	}
	<#-- method -->
	
	<#-- ./method -->
	</#list>

}
