package ${packageName};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${mapperPackageName}.${mapperJavaName};
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.json.JsonMapper;

/**
 * @Description ${desc!''}
 * @Author ${author!''}
 * @Date ${date!''}
 * @Version ${version!''}
 */
@Service(value = "${packageName}.${javaName}")
@Transactional(readOnly = true)
public class ${javaName} {

	protected static final Logger logger = LoggerFactory.getLogger(${javaName}.class);

	@Autowired
    protected ${mapperJavaName} ${mapperJavaName?uncap_first};
	
	<#list methods as method>
	<#if method.methodMimeType.method == 'POST' || method.methodMimeType.method == 'PUT' || method.methodMimeType.method == 'DELETE'>
	@Transactional(readOnly = false)
	</#if>
	public
	<#if method.hasResult == 'Y'>
	<#if method.response.json.type == 2><#-- no data -->
	void
	<#elseif method.response.json.type == 3><#-- object -->
	${controllerPackageName}.${controllerJavaName}.${method.response.className}
	<#elseif method.response.json.type == 4><#-- list -->
	java.util.List<${controllerPackageName}.${controllerJavaName}.${method.response.className}>
	<#elseif method.response.json.type == 5><#-- page -->
	Page
	</#if>
	<#else>
	void
	</#if>
	${method.methodName}(${controllerPackageName}.${controllerJavaName}.${method.request.className} ${method.request.className?uncap_first}) {
		// TODO
		<#if method.hasResult == 'Y'>
		<#if method.response.json.type == 3><#-- object -->
		${controllerPackageName}.${controllerJavaName}.${method.response.className} obj = JsonMapper.fromJsonString("${method.response.json.exampleData}", ${controllerPackageName}.${controllerJavaName}.${method.response.className}.class);
		return obj;
		<#elseif method.response.json.type == 4><#-- list -->
		java.util.List<${controllerPackageName}.${controllerJavaName}.${method.response.className}> list = JsonMapper.fromJsonListString("${method.response.json.exampleData}", ${controllerPackageName}.${controllerJavaName}.${method.response.className}.class);
		return list;
		<#elseif method.response.json.type == 5><#-- page -->
		java.util.List<${controllerPackageName}.${controllerJavaName}.${method.response.className}> list = JsonMapper.fromJsonListString("${method.response.json.exampleData}", ${controllerPackageName}.${controllerJavaName}.${method.response.className}.class);
		Page page = new Page();
		page.setPageNo(3);
		page.setPageSize(10);
		page.setCount(100);
		page.setDataList(list);
		return page;
		</#if>
		</#if>
	}
	<#-- method -->
	
	<#-- ./method -->
	</#list>

}
