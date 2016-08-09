package ${packageName}.base.entity;

import org.apache.commons.lang3.StringUtils;

public class Base${javaName} extends ${entityPackageName}.${javaName}
<#if table.entityType == 1>
 implements com.github.hualuomoli.base.BasePersistent
<#elseif table.entityType == 2>
 implements com.github.hualuomoli.base.BasePersistent, com.github.hualuomoli.base.CommonPersistent
</#if>
 {

	<#list table.queryColumns as queryColumn>
	/** ${queryColumn.comment!''} */
	private ${queryColumn.javaTypeName} ${queryColumn.javaName};
	</#list>
	
	public Base${javaName}(){
	}
	
	<#if uniques?? && uniques?size gt 0>
	public Base${javaName}(
	<#list uniques as unique>
		${unique.javaTypeName} ${unique.javaName}<#if uniques?size - unique_index gt 1>,</#if>
	</#list>
	){
		<#list uniques as unique>
		this.set${unique.javaName?cap_first}(${unique.javaName});
		</#list>
	}
	</#if>
	
	<#list table.queryColumns as queryColumn>
	public ${queryColumn.javaTypeName} get${queryColumn.javaName?cap_first}() {
		return ${queryColumn.javaName};
	}
	
	public void set${queryColumn.javaName?cap_first}(${queryColumn.javaTypeName} ${queryColumn.javaName}) {
		<#if queryColumn.like>
		if (StringUtils.isBlank(${queryColumn.javaName})) {
			return;
		}
		this.${queryColumn.javaName} = ${queryColumn.likeJavaName};
		<#elseif queryColumn.array>
		if (${queryColumn.javaName} == null || ${queryColumn.javaName}.length == 0) {
			return;
		}
		this.${queryColumn.javaName} = ${queryColumn.javaName};
		<#else>
		this.${queryColumn.javaName} = ${queryColumn.javaName};
		</#if>
	}
	</#list>

}
