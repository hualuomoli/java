package com.github.hualuomoli.extend.base.service.impl;

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
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination.QueryType;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.extend.base.entity.BaseSms;
import com.github.hualuomoli.extend.base.mapper.BaseSmsMapper;
import com.github.hualuomoli.extend.base.service.BaseSmsService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseSms
@Service(value = "com.github.hualuomoli.extend.base.service.BaseSmsServiceImpl")
@Transactional(readOnly = true)
public class BaseSmsServiceImpl implements BaseSmsService {

	@Autowired
	private BaseSmsMapper baseSmsMapper;
	
	@Override
	public BaseSms get(BaseSms baseSms) {
		return this.get(baseSms.getId());
	}
	
	@Override
	public BaseSms get(String id) {
		return baseSmsMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert BaseSms baseSms) {
		return baseSmsMapper.insert(baseSms);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseSms> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseSms> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseSmsMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseSms baseSms) {
		return baseSmsMapper.update(baseSms);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete BaseSms baseSms) {
		return baseSmsMapper.update(baseSms);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		BaseSms temp = new BaseSms();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(BaseSms baseSms) {
		return this.delete(baseSms.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseSmsMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseSmsMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		if (ids == null || ids.size() == 0) {
			return 0;
		}
		return this.deleteByIds(ids.toArray(new String[]{}));
	}

	@Override
	public List<BaseSms> findList(BaseSms baseSms) {
		return baseSmsMapper.findList(baseSms);
	}
	
	@Override
	public List<BaseSms> findList(BaseSms baseSms, String... orderByStrArray) {
		return this.findList(baseSms, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseSms> findList(BaseSms baseSms, Order... orders) {
		return this.findList(baseSms, new Pagination(orders));
	}

	@Override
	public List<BaseSms> findList(BaseSms baseSms, List<Order> orders) {
		return this.findList(baseSms, new Pagination(orders));
	}
	
	private List<BaseSms> findList(BaseSms baseSms, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseSms, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseSms baseSms) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseSms, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize) {
		return this.findPage(baseSms, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseSms, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseSms, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseSms baseSms, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseSms, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseSms baseSms, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseSms> list = baseSmsMapper.findList(baseSms);
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
