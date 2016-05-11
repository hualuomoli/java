package ${packageName};

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.hualuomoli.base.stereotype.Mapper;
import ${entity.fullName};

// ${r"#"}${entity.simpleName}
@Mapper(value = "${fullName}")
public interface ${name} {

	${entity.simpleName} get(String id);

	int insert(${entity.simpleName} ${entity.simpleName?uncap_first});
	
	int batchInsert(List<${entity.simpleName}> list);

	int update(${entity.simpleName} ${entity.simpleName?uncap_first});

	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<${entity.simpleName}> findList(${entity.simpleName} ${entity.simpleName?uncap_first});

}
