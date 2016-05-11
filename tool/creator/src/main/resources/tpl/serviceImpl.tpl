package ${packageName};

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.base.util.BaseUtils.ListSplit;
import ${entity.fullName};
import ${mapper.fullName};

// ${r"#"}${entity.simpleName}
@Service(value = "${fullName}Impl")
public class ${name}Impl implements ${name} {

	@Autowired
	private ${mapper.name} ${mapper.name?uncap_first};
	
	@Override
	public ${entity.simpleName} get(String id) {
		return ${mapper.name?uncap_first}.get(id);
	}

	@Override
	public void insert(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		${entity.simpleName?uncap_first}.preInsert();
		${mapper.name?uncap_first}.insert(${entity.simpleName?uncap_first});
	}
	
	@Override
	public void batchInsert(List<${entity.simpleName}> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		
		BaseUtils.preBatchInsert(list);

		ListSplit listSplit = new ListSplit(BaseUtils.getBatchMaxCount());
		while (true) {
			List<CreatorDemo> newList = BaseUtils.getList(list, listSplit);
			if (newList.size() == 0) {
				break;
			}
			${mapper.name?uncap_first}.batchInsert(newList);
		}
	}

	@Override
	public void update(${entity.simpleName} ${entity.simpleName?uncap_first}) {
		${entity.simpleName?uncap_first}.preUpdate();
		${mapper.name?uncap_first}.update(${entity.simpleName?uncap_first});
	}

	@Override
	public void delete(String id) {
		${mapper.name?uncap_first}.delete(id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		${mapper.name?uncap_first}.deleteByIds(ids);
	}
	
	@Override
	public void deleteByIds(Collection<String> ids) {
		${mapper.name?uncap_first}.deleteByIds(ids);
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
