package ${packageName}.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${packageName}.base.entity.Base${javaName};
import ${entityPackageName}.${javaName};

// ${r"#"}Base${javaName}
@Repository(value = "${packageName}.base.mapper.Base${javaName}Mapper")
public interface Base${javaName}Mapper {

	<#-- Base${javaName} get(${javaName} ${javaName?uncap_first}); -->
	
	Base${javaName} get(String id);

	int insert(${javaName} ${javaName?uncap_first});
	
	<T extends ${javaName}> int batchInsert(@Param(value = "list") List<T> list);

	int update(${javaName} ${javaName?uncap_first});

	<#-- int delete(${javaName} ${javaName?uncap_first}); -->
	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	<#-- int deleteByIds(@Param(value = "ids") Collection<String> ids); -->

	List<Base${javaName}> findList(Base${javaName} base${javaName});

}
