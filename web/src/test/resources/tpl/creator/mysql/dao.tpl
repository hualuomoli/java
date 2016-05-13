package ${packageName};

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${entityPackageName}.${entityJavaName};

// ${r"#"}${entityJavaName}
@Repository(value = "${packageName}.${javaName}")
public interface ${javaName} {

	${entityJavaName} get(${entityJavaName} ${entityJavaName?uncap_first});
	
	${entityJavaName} get(String id);

	int insert(${entityJavaName} ${entityJavaName?uncap_first});
	
	int batchInsert(@Param(value = "list") List<${entityJavaName}> list);

	int update(${entityJavaName} ${entityJavaName?uncap_first});

	int delete(${entityJavaName} ${entityJavaName?uncap_first});
	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<${entityJavaName}> findList(${entityJavaName} ${entityJavaName?uncap_first});

}
