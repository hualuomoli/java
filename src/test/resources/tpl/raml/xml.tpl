<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${javaName}">

	<#list methods as method>
	<#if method.hasResult == 'Y' && method.response.json.type != 2>
	<select id="${method.methodName}" resultType="${controllerPackageName}.${controllerJavaName}.${method.response.className}">
	
	</select>
	</#if>
	</#list>
	
</mapper>