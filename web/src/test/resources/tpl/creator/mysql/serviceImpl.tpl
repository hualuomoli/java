package ${packageName};

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import ${entityPackageName}.${entityJavaName};
import ${mapperPackageName}.${mapperJavaName};
import com.github.hualuomoli.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.plugin.mybatis.interceptor.pagination.PaginationInterceptor;


// ${r"#"}${entityJavaName}
@Service(value = "${packageName}.${javaName}")
@Transactional(readOnly = true)
public class ${javaName} implements ${implements} {

	@Autowired
	private ${mapperJavaName} ${mapperJavaName?uncap_first};
	
	@Override
	public ${entityJavaName} get(${entityJavaName} ${entityJavaName?uncap_first}) {
		return ${mapperJavaName?uncap_first}.get(${entityJavaName?uncap_first});
	}
	
	@Override
	public ${entityJavaName} get(String id) {
		return ${mapperJavaName?uncap_first}.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(${entityJavaName} ${entityJavaName?uncap_first}) {
		// ${entityJavaName?uncap_first}.preInsert();
		return ${mapperJavaName?uncap_first}.insert(${entityJavaName?uncap_first});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(List<${entityJavaName}> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		
		// BaseUtils.preBatchInsert(list);

		Config config = new Config(100);
		while (true) {
			List<${entityJavaName}> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			${mapperJavaName?uncap_first}.batchInsert(newList);
		}
		return list.size();
	}

	@Override
	@Transactional(readOnly = false)
	public int update(${entityJavaName} ${entityJavaName?uncap_first}) {
		// ${entityJavaName?uncap_first}.preUpdate();
		return ${mapperJavaName?uncap_first}.update(${entityJavaName?uncap_first});
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(${entityJavaName} ${entityJavaName?uncap_first}) {
		return ${mapperJavaName?uncap_first}.delete(${entityJavaName?uncap_first});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return ${mapperJavaName?uncap_first}.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		return ${mapperJavaName?uncap_first}.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		return ${mapperJavaName?uncap_first}.deleteByIds(ids);
	}

	@Override
	public List<${entityJavaName}> findList(${entityJavaName} ${entityJavaName?uncap_first}) {
		return ${mapperJavaName?uncap_first}.findList(${entityJavaName?uncap_first});
	}
	
	@Override
	public Page findPage(${entityJavaName} ${entityJavaName?uncap_first}, Integer pageNumber, Integer pageSize) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNumber);
		pagination.setPageSize(pageSize);

		// set local thread
		PaginationInterceptor.setPagination(pagination);
		// query
		List<${entityJavaName}> list = ${mapperJavaName?uncap_first}.findList(${entityJavaName?uncap_first});
		// get local thread
		pagination = PaginationInterceptor.getPagination();
		// remove local thread
		PaginationInterceptor.clearPagination();

		// set page
		Page page = new Page();
		page.setCount(pagination.getCount());
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		page.setDataList(list);

		return page;
	}
	
}
