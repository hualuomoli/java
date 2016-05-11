package ${packageName};

import java.util.Collection;
import java.util.List;

import com.github.hualuomoli.base.entity.Pagination;
import ${entity.fullName};

// ${r"#"}${entity.simpleName}
public interface ${name} {

	${entity.simpleName} get(String id);

	void insert(${entity.simpleName} ${entity.simpleName?uncap_first});
	
	void batchInsert(List<${entity.simpleName}> list);

	void update(${entity.simpleName} ${entity.simpleName?uncap_first});

	void delete(String id);
	
	void deleteByIds(String[] ids);
	
	void deleteByIds(Collection<String> ids);

	List<${entity.simpleName}> findList(${entity.simpleName} ${entity.simpleName?uncap_first});

	Pagination findPage(${entity.simpleName} ${entity.simpleName?uncap_first}, Pagination pagination);
	
}
