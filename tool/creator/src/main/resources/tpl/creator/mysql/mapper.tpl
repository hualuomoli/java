package ${packageName}.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${packageName}.base.entity.Base${javaName};

// ${r"#"}Base${javaName}
@Repository(value = "${packageName}.base.mapper.Base${javaName}Mapper")
public interface Base${javaName}Mapper {

	Base${javaName} get(Base${javaName} base${javaName});
	
	Base${javaName} get(String id);

	int insert(Base${javaName} base${javaName});
	
	int batchInsert(@Param(value = "list") List<Base${javaName}> list);

	int update(Base${javaName} base${javaName});

	int delete(Base${javaName} base${javaName});
	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	
	int deleteByIds(@Param(value = "ids") Collection<String> ids);

	List<Base${javaName}> findList(Base${javaName} base${javaName});

}
