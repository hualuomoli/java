package ${packageName}.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import ${packageName}.base.entity.Base${javaName};
import ${entityPackageName}.${javaName};
<#if table.tree>
import com.github.hualuomoli.extend.tree.service.TreeService.TreeDealer;
</#if>

// ${r"#"}Base${javaName}
public interface Base${javaName}Service<#if table.tree> extends TreeDealer<Base${javaName}></#if> {

	Base${javaName} get(${javaName} ${javaName?uncap_first});
	
	Base${javaName} get(String id);
	
	<#if uniques?? && uniques?size gt 0>
	Base${javaName} getUnique(
	<#list uniques as unique>
		${unique.javaTypeName} ${unique.javaName}<#if uniques?size - unique_index gt 1>,</#if>
	</#list>
	);
	</#if>
	
	int insert(${javaName} ${javaName?uncap_first});
	
	<T extends ${javaName}> int batchInsert(List<T> list);

	int update(${javaName} ${javaName?uncap_first});
	
	<#if table.entityType == 1>
	<#elseif table.entityType == 2>
	 int logicalDelete(${javaName} ${javaName?uncap_first});

	 int logicalDelete(String id);
	</#if>
	

	int delete(${javaName} ${javaName?uncap_first});
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<Base${javaName}> findList(Base${javaName} base${javaName});
	
	List<Base${javaName}> findList(Base${javaName} base${javaName}, String... orderByStrArray);

	List<Base${javaName}> findList(Base${javaName} base${javaName}, Order... orders);

	List<Base${javaName}> findList(Base${javaName} base${javaName}, List<Order> orders);
	
	Integer getTotal(Base${javaName} base${javaName});
	
	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(Base${javaName} base${javaName}, Pagination pagination);
	
}
