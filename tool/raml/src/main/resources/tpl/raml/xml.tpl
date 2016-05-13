<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${mapperJavaName}">

	<#list methods as method>
	<#if method.response.resJson.type != 2>
	<select id="${method.methodName}" resultType="${packageName}.entity.${method.response.className}">
	
	</select>
	</#if>
	</#list>
	
</mapper>