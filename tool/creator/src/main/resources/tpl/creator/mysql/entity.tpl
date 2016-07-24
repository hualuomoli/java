package ${packageName}.base.entity;

import org.apache.commons.lang3.StringUtils;

import com.github.hualuomoli.base.Persistent;

public class Base${javaName} extends ${entityPackageName}.${javaName} implements Persistent {

	<#list table.queryColumns as queryColumn>
	/** ${queryColumn.comment!''} */
	private ${queryColumn.javaTypeName} ${queryColumn.javaName};
	</#list>
	
	public Base${javaName}(){
	}
	
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
