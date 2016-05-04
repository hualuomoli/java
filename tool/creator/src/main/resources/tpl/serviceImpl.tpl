package ${packageName}.orm;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import ${entity.fullName};
import ${mapper.fullName};
import ${fullName};

// ${r"#"}${entity.simpleName}
@Service(value = "${packageName}.orm.${name}Impl")
@Transactional(readOnly = true)
public class ${name}Impl implements ${name} {

	@Autowired
	private ${mapper.name} ${mapper.name?uncap_first};
	
	@Override
	public ${entity.simpleName} get(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		return ${mapper.name?uncap_first}.get(${entity.simpleName?uncap_first});
	}
	
	@Override
	public ${entity.simpleName} get(String id) {
		return ${mapper.name?uncap_first}.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		${entity.simpleName?uncap_first}.preInsert();
		return ${mapper.name?uncap_first}.insert(${entity.simpleName?uncap_first});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(List<${entity.simpleName}> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}
		
		BaseUtils.preBatchInsert(list);

		Config config = new Config(BaseUtils.getBatchMaxCount());
		while (true) {
			List<${entity.simpleName}> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			${mapper.name?uncap_first}.batchInsert(newList);
		}
		return list.size();
	}

	@Override
	@Transactional(readOnly = false)
	public int update(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		${entity.simpleName?uncap_first}.preUpdate();
		return ${mapper.name?uncap_first}.update(${entity.simpleName?uncap_first});
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		return ${mapper.name?uncap_first}.delete(${entity.simpleName?uncap_first});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return ${mapper.name?uncap_first}.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		return ${mapper.name?uncap_first}.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		return ${mapper.name?uncap_first}.deleteByIds(ids);
	}

	@Override
	public List<${entity.simpleName}> findList(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		return ${mapper.name?uncap_first}.findList(${entity.simpleName?uncap_first});
	}
	
	@Override
	public Pagination findPage(${entity.simpleName} ${entity.simpleName?uncap_first}, Pagination pagination) {
		${entity.simpleName?uncap_first}.setPagination(pagination);
		pagination.setDataList(${mapper.name?uncap_first}.findList(${entity.simpleName?uncap_first}));
		return pagination;
	}
	
}
