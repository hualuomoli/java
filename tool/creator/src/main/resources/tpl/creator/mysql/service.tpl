package ${packageName}.base.service;

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import ${packageName}.base.entity.Base${javaName};

// ${r"#"}Base${javaName}
public interface Base${javaName}Service {

	Base${javaName} get(Base${javaName} base${javaName});
	
	Base${javaName} get(String id);
	
	<#if unique??>
	Base${javaName} getBy${unique.javaName?cap_first}(${unique.javaTypeName} ${unique.javaName});
	</#if>

	void insert(Base${javaName} base${javaName});
	
	void batchInsert(List<Base${javaName}> list);

	void update(Base${javaName} base${javaName});
	
	void logicalDelete(Base${javaName} base${javaName});

	void logicalDelete(String id);

	void delete(Base${javaName} base${javaName});
	
	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

	List<Base${javaName}> findList(Base${javaName} base${javaName});
	
	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(Base${javaName} base${javaName}, Pagination pagination);
	
}
