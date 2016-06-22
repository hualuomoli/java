package ${packageName};

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Page;
import ${entityPackageName}.${entityJavaName};

// ${r"#"}${entityJavaName}
public interface ${javaName} {

	${entityJavaName} get(${entityJavaName} ${entityJavaName?uncap_first});
	
	${entityJavaName} get(String id);

	int insert(${entityJavaName} ${entityJavaName?uncap_first});
	
	int batchInsert(List<${entityJavaName}> list);

	int update(${entityJavaName} ${entityJavaName?uncap_first});

	int delete(${entityJavaName} ${entityJavaName?uncap_first});
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<${entityJavaName}> findList(${entityJavaName} ${entityJavaName?uncap_first});

	Page findPage(${entityJavaName} ${entityJavaName?uncap_first}, Integer pageNumber, Integer pageSize);
	
}
