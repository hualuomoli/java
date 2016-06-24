package ${packageName};

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description ${desc!''}
 * @Author ${author!''}
 * @Date ${date!''}
 * @Version ${version!''}
 */
@Repository(value = "${packageName}.${javaName}")
public interface ${javaName} {

	<#list methods as method>
	<#if method.hasResult == 'Y' && method.response.json.type != 2>
	<#if method.response.json.type == 3><#-- object -->
	${controllerPackageName}.${controllerJavaName}.${method.response.className}
	<#elseif method.response.json.type == 4><#-- list -->
	java.util.List<${controllerPackageName}.${controllerJavaName}.${method.response.className}>
	<#elseif method.response.json.type == 5><#-- page -->
	java.util.List<${controllerPackageName}.${controllerJavaName}.${method.response.className}>
	</#if>
	${method.methodName}(${controllerPackageName}.${controllerJavaName}.${method.request.className} ${method.request.className?uncap_first});
	</#if>
	</#list>

}
