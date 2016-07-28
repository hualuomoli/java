package ${packageName}.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

<#list methods as method>
import ${packageName}.entity.${method.request.className};
import ${packageName}.entity.${method.response.className};
</#list>

/**
 * @Description ${desc!''}
 * @Author ${author}
 * @Date ${date}
 * @Version ${version}
 */
@Repository(value = "${packageName}.${mapperJavaName}")
public interface ${mapperJavaName} {

	<#list methods as method>
	<#if method.response.resJson.type != 2>
	<#if method.response.resJson.type == 3>
	<#-- object -->
	${method.response.className}
	<#elseif method.response.resJson.type == 4>
	<#-- list -->
	java.util.List<${method.response.className}>
	<#elseif method.response.resJson.type == 5>
	<#-- page -->
	java.util.List<${method.response.className}>
	</#if>
	${method.methodName}(${method.request.className} ${method.request.className?uncap_first});
	
	</#if>
	</#list>

}
