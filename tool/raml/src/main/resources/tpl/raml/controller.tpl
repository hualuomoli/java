package ${packageName}.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import ${restResponsePackageName}.${restResponseClassName};
import com.github.hualuomoli.base.entity.Page;
<#list methods as method>
import ${packageName}.entity.${method.request.className};
import ${packageName}.entity.${method.response.className};
</#list>
import ${packageName}.service.${serviceJavaName};
import com.github.hualuomoli.mvc.annotation.RequestVersion;

/**
 * @Description ${desc!''}
 * @Author ${author}
 * @Date ${date}
 * @Version ${version}
 */
@RequestVersion(value = "${version}")
@RequestMapping(value = "${uri}")
@RestController(value = "${packageName}.${controllerJavaName}")
public class ${controllerJavaName} {
	
	@Autowired
	private ${serviceJavaName} ${serviceJavaName?uncap_first};
	
	<#list methods as method>
	<#-- 方法 -->
	/**
	 * ${method.description!''}
	<#list method.uriParams as uriParam>
	 * @param ${uriParam.name} ${uriParam.comment}
	</#list>
	<#list method.fileParams as fileParam>
	 * @param ${fileParam.name} ${fileParam.comment}
	</#list>
	 */
	@RequestMapping(value = "${method.methodMimeType.uri}", method = RequestMethod.${method.methodMimeType.method}<#if method.methodMimeType.consumes??>, consumes = { "${method.methodMimeType.consumes}" }</#if><#if method.methodMimeType.produces??>, produces = { "${method.methodMimeType.produces}" }</#if>)
	public String ${method.methodName}(
	<#list method.uriParams as uriParam>
	<#list uriParam.annos as anno>
	${anno}
	</#list>
	${uriParam.type} ${uriParam.name},
	</#list>
	<#list method.fileParams as fileParam>
	<#list fileParam.annos as anno>
	${anno}
	</#list>
	${fileParam.type} ${fileParam.name},
	</#list>
	<#if method.request.anno??>
	${method.request.anno} 
	</#if>
	${method.request.className} ${method.request.className?uncap_first},
	HttpServletRequest request, 
	HttpServletResponse response
	) {
		// 设置属性
		<#list method.uriParams as uriParam>
		${method.request.className?uncap_first}.set${uriParam.name?cap_first}(${uriParam.name});
		</#list>
		<#list method.fileParams as fileParam>
		${method.request.className?uncap_first}.set${fileParam.name?cap_first}(${fileParam.name});
		</#list>
		
		<#-- 根据不同返回类型,返回数据 -->
		<#if method.response.resJson.type == 2>
		<#-- 不返回业务数据 -->
		${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getNoData();
		<#elseif method.response.resJson.type == 3>
		<#-- Object -->
		${method.response.className} ${method.response.className?uncap_first} = ${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getObjectData("${method.response.resJson.resultName}", ${method.response.className?uncap_first});
		<#elseif method.response.resJson.type == 4>
		<#-- list -->
		java.util.List<${method.response.className}> list = ${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getListData("${method.response.resJson.resultName}", list);
		<#elseif method.response.resJson.type == 5>
		<#-- page -->
		Page page = ${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getPageData("${method.response.resJson.pageName}", "${method.response.resJson.resultName}", page);
		<#elseif method.response.resJson.type == 6>
		<#-- origin object -->
		${method.response.className} ${method.response.className?uncap_first} = ${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getOriginData(${method.response.className?uncap_first});
		<#elseif method.response.resJson.type == 7>
		<#-- origin list -->
		java.util.List<${method.response.className}> list = ${serviceJavaName?uncap_first}.${method.methodName}(${method.request.className?uncap_first});
		return ${restResponseClassName}.getOriginData(list);
		</#if>
		
	}
	</#list>
	
}
