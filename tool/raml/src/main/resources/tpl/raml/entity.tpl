package ${packageName};

import java.util.Date;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityIgnore;
import com.github.hualuomoli.base.annotation.entity.EntityTable;
import com.github.hualuomoli.base.annotation.entity.EntityColumnType;

/**
 * @Description ${desc}
 * @Author ${author}
 * @Date ${date}
 * @Version ${version}
 */
@EntityTable
public class ${entity.name} {

	<#list entity.columnList as column>
	@EntityColumn(comment = "${column.comment}")
	private ${column.type} ${column.name};
	</#list>

	<#list entity.dependentList as dependent>
	<#if dependent.relation == 1>
	private ${dependent.type} ${dependent.name};
	<#elseif dependent.relation == 2>
	private java.util.List<${dependent.type}> ${dependent.name};
	</#if>
	</#list>

	<#list entity.columnList as column>
	public ${column.type} get${column.name?cap_first}(){
		return ${column.name};
	}

	public void set${column.name?cap_first}(${column.type} ${column.name}){
		this.${column.name} = ${column.name};
	}
	</#list>

	<#list entity.dependentList as dependent>
	<#if dependent.relation == 1>
	public ${dependent.type} get${dependent.name?cap_first}(){
		return ${dependent.name};
	}

	public void set${dependent.name?cap_first}(${dependent.type} ${dependent.name}){
		this.${dependent.name} = ${dependent.name};
	}
	<#elseif dependent.relation == 2>
	public java.util.List<${dependent.type}> get${dependent.name?cap_first}(){
		return ${dependent.name};
	}

	public void set${dependent.name?cap_first}(java.util.List<${dependent.type}> ${dependent.name}){
		this.${dependent.name} = ${dependent.name};
	}
	</#if>
	</#list>


}
