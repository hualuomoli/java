package com.github.hualuomoli.plugin.roll.base.service.impl;

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
import com.github.hualuomoli.plugin.roll.base.entity.BasePollFrequency;
import com.github.hualuomoli.plugin.roll.entity.PollFrequency;
import com.github.hualuomoli.plugin.roll.base.mapper.BasePollFrequencyMapper;
import com.github.hualuomoli.plugin.roll.base.service.BasePollFrequencyService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BasePollFrequency
@Service(value = "com.github.hualuomoli.plugin.roll.base.service.BasePollFrequencyServiceImpl")
@Transactional(readOnly = true)
public class BasePollFrequencyServiceImpl implements BasePollFrequencyService {

	@Autowired
	private BasePollFrequencyMapper basePollFrequencyMapper;
	
	@Override
	public BasePollFrequency get(PollFrequency pollFrequency) {
		return this.get(pollFrequency.getId());
	}
	
	@Override
	public BasePollFrequency get(String id) {
		return basePollFrequencyMapper.get(id);
	}
	
	@Override
	public BasePollFrequency getUnique(
		java.lang.String dataId
	) {
		BasePollFrequency basePollFrequency = new BasePollFrequency();
		basePollFrequency.setDataId(dataId);
		List<BasePollFrequency> list = this.findList(basePollFrequency);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() != 1) {
			throw new MoreDataFoundException();
		}
		return list.get(0);
	}

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert PollFrequency pollFrequency) {
		return basePollFrequencyMapper.insert(pollFrequency);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends PollFrequency> int batchInsert(@PreBatchInsert  List<T> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<T> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += basePollFrequencyMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate PollFrequency pollFrequency) {
		return basePollFrequencyMapper.update(pollFrequency);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(PollFrequency pollFrequency) {
		return this.delete(pollFrequency.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return basePollFrequencyMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return basePollFrequencyMapper.deleteByIds(ids);
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
	public List<BasePollFrequency> findList(BasePollFrequency basePollFrequency) {
		return basePollFrequencyMapper.findList(basePollFrequency);
	}
	
	@Override
	public List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, String... orderByStrArray) {
		return this.findList(basePollFrequency, new Pagination(orderByStrArray));
	}

	@Override
	public List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, Order... orders) {
		return this.findList(basePollFrequency, new Pagination(orders));
	}

	@Override
	public List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, List<Order> orders) {
		return this.findList(basePollFrequency, new Pagination(orders));
	}
	
	private List<BasePollFrequency> findList(BasePollFrequency basePollFrequency, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(basePollFrequency, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BasePollFrequency basePollFrequency) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(basePollFrequency, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize) {
		return this.findPage(basePollFrequency, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(basePollFrequency, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(basePollFrequency, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BasePollFrequency basePollFrequency, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(basePollFrequency, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BasePollFrequency basePollFrequency, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BasePollFrequency> list = basePollFrequencyMapper.findList(basePollFrequency);
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
