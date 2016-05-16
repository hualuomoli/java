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

	int insert(Base${javaName} base${javaName});
	
	int batchInsert(List<Base${javaName}> list);

	int update(Base${javaName} base${javaName});
	
	int logicalDelete(Base${javaName} base${javaName});

	int logicalDelete(String id);

	int delete(Base${javaName} base${javaName});
	
	int delete(String id);
	
	int deleteByIds(String[] ids);
	
	int deleteByIds(Collection<String> ids);

	List<Base${javaName}> findList(Base${javaName} base${javaName});
	
	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, String... orderByStrArray);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, Order... orders);

	Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, List<Order> orders);

	Page findPage(Base${javaName} base${javaName}, Pagination pagination);
	
}
