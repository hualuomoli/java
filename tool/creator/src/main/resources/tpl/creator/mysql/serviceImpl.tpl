package ${packageName}.base.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PrePersistent;
import com.github.hualuomoli.base.annotation.persistent.Type;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import ${packageName}.base.entity.Base${javaName};
import ${packageName}.base.mapper.Base${javaName}Mapper;
import ${packageName}.base.service.Base${javaName}Service;


// ${r"#"}Base${javaName}
@Service(value = "${packageName}.base.service.Base${javaName}ServiceImpl")
@Transactional(readOnly = true)
public class Base${javaName}ServiceImpl implements Base${javaName}Service {

	@Autowired
	private Base${javaName}Mapper base${javaName}Mapper;
	
	@Override
	public Base${javaName} get(Base${javaName} base${javaName}) {
		return base${javaName}Mapper.get(base${javaName});
	}
	
	@Override
	public Base${javaName} get(String id) {
		return base${javaName}Mapper.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(@PrePersistent(type = Type.INSERT) Base${javaName} base${javaName}) {
		return base${javaName}Mapper.insert(base${javaName});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PrePersistent(type = Type.BATCH_INSERT)  List<Base${javaName}> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		
		Config config = new Config(100);
		while (true) {
			List<Base${javaName}> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			base${javaName}Mapper.batchInsert(newList);
		}
		return list.size();
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PrePersistent(type = Type.UPDATE)  Base${javaName} base${javaName}) {
		return base${javaName}Mapper.update(base${javaName});
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(Base${javaName} base${javaName}) {
		Base${javaName} temp = new Base${javaName}();
		temp.setId(base${javaName}.getId());
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		Base${javaName} temp = new Base${javaName}();
		temp.setId(id);
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Base${javaName} base${javaName}) {
		return base${javaName}Mapper.delete(base${javaName});
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return base${javaName}Mapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		return base${javaName}Mapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		return base${javaName}Mapper.deleteByIds(ids);
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
