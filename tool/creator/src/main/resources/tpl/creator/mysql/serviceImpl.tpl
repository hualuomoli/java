package ${packageName}.base.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PreBatchInsert;
import com.github.hualuomoli.base.annotation.persistent.PreDelete;
import com.github.hualuomoli.base.annotation.persistent.PreInsert;
import com.github.hualuomoli.base.annotation.persistent.PreUpdate;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import ${packageName}.base.entity.Base${javaName};
import ${packageName}.base.mapper.Base${javaName}Mapper;
import ${packageName}.base.service.Base${javaName}Service;
import com.github.hualuomoli.exception.MoreDataFoundException;

// ${r"#"}Base${javaName}
@Service(value = "${packageName}.base.service.Base${javaName}ServiceImpl")
@Transactional(readOnly = true)
public class Base${javaName}ServiceImpl implements Base${javaName}Service {

	@Autowired
	private Base${javaName}Mapper base${javaName}Mapper;
	
	@Override
	public Base${javaName} get(Base${javaName} base${javaName}) {
		return this.get(base${javaName}.getId());
	}
	
	@Override
	public Base${javaName} get(String id) {
		return base${javaName}Mapper.get(id);
	}
	
	<#if unique??>
	@Override
	public Base${javaName} getBy${unique.javaName?cap_first}(${unique.javaTypeName} ${unique.javaName}) {
		Base${javaName} base${javaName} = new Base${javaName}();
		base${javaName}.set${unique.javaName?cap_first}(${unique.javaName});
		List<Base${javaName}> list = this.findList(base${javaName});
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() != 1) {
			throw new MoreDataFoundException();
		}
		return list.get(0);
	}
	</#if>

	@Override
	@Transactional(readOnly = false)
	public void insert(@PreInsert Base${javaName} base${javaName}) {
		base${javaName}Mapper.insert(base${javaName});
	}
	
	@Override
	@Transactional(readOnly = false)
	public void batchInsert(@PreBatchInsert  List<Base${javaName}> list) {
		if (list == null || list.size() == 0) {
			return;
		}	
		
		Config config = new Config(100);
		while (true) {
			List<Base${javaName}> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			base${javaName}Mapper.batchInsert(newList);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(@PreUpdate Base${javaName} base${javaName}) {
		base${javaName}Mapper.update(base${javaName});
	}

	@Override
	@Transactional(readOnly = false)
	public void logicalDelete(@PreDelete Base${javaName} base${javaName}) {
		base${javaName}Mapper.update(base${javaName});
	}
	
	@Override
	@Transactional(readOnly = false)
	public void logicalDelete(String id) {
		Base${javaName} temp = new Base${javaName}();
		temp.setId(id);
		this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Base${javaName} base${javaName}) {
		this.delete(base${javaName}.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		base${javaName}Mapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		base${javaName}Mapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(Collection<String> ids) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		this.deleteByIds(ids.toArray(new String[]{}));
	}

	@Override
	public List<Base${javaName}> findList(Base${javaName} base${javaName}) {
		return base${javaName}Mapper.findList(base${javaName});
	}
	
	@Override
	public Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize) {
		return this.findPage(base${javaName}, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(base${javaName}, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(base${javaName}, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(Base${javaName} base${javaName}, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(base${javaName}, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(Base${javaName} base${javaName}, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<Base${javaName}> list = base${javaName}Mapper.findList(base${javaName});
		// get local thread and remove
		pagination = PaginationInterceptor.popPagination();

		// set page
		Page page = new Page();
		page.setCount(pagination.getCount());
		page.setPageNo(pagination.getPageNo());
		page.setPageSize(pagination.getPageSize());
		page.setDataList(list);

		return page;
	}
	
}
