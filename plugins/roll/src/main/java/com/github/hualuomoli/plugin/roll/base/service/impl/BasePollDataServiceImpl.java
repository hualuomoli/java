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
import com.github.hualuomoli.plugin.roll.base.entity.BasePollData;
import com.github.hualuomoli.plugin.roll.entity.PollData;
import com.github.hualuomoli.plugin.roll.base.mapper.BasePollDataMapper;
import com.github.hualuomoli.plugin.roll.base.service.BasePollDataService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BasePollData
@Service(value = "com.github.hualuomoli.plugin.roll.base.service.BasePollDataServiceImpl")
@Transactional(readOnly = true)
public class BasePollDataServiceImpl implements BasePollDataService {

	@Autowired
	private BasePollDataMapper basePollDataMapper;
	
	@Override
	public BasePollData get(PollData pollData) {
		return this.get(pollData.getId());
	}
	
	@Override
	public BasePollData get(String id) {
		return basePollDataMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert PollData pollData) {
		return basePollDataMapper.insert(pollData);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends PollData> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += basePollDataMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate PollData pollData) {
		return basePollDataMapper.update(pollData);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(PollData pollData) {
		return this.delete(pollData.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return basePollDataMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return basePollDataMapper.deleteByIds(ids);
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
	public List<BasePollData> findList(BasePollData basePollData) {
		return basePollDataMapper.findList(basePollData);
	}
	
	@Override
	public List<BasePollData> findList(BasePollData basePollData, String... orderByStrArray) {
		return this.findList(basePollData, new Pagination(orderByStrArray));
	}

	@Override
	public List<BasePollData> findList(BasePollData basePollData, Order... orders) {
		return this.findList(basePollData, new Pagination(orders));
	}

	@Override
	public List<BasePollData> findList(BasePollData basePollData, List<Order> orders) {
		return this.findList(basePollData, new Pagination(orders));
	}
	
	private List<BasePollData> findList(BasePollData basePollData, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(basePollData, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BasePollData basePollData) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(basePollData, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize) {
		return this.findPage(basePollData, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(basePollData, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(basePollData, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BasePollData basePollData, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(basePollData, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BasePollData basePollData, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BasePollData> list = basePollDataMapper.findList(basePollData);
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
