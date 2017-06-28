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
import com.github.hualuomoli.plugin.roll.base.entity.BasePollErrorHis;
import com.github.hualuomoli.plugin.roll.entity.PollErrorHis;
import com.github.hualuomoli.plugin.roll.base.mapper.BasePollErrorHisMapper;
import com.github.hualuomoli.plugin.roll.base.service.BasePollErrorHisService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BasePollErrorHis
@Service(value = "com.github.hualuomoli.plugin.roll.base.service.BasePollErrorHisServiceImpl")
@Transactional(readOnly = true)
public class BasePollErrorHisServiceImpl implements BasePollErrorHisService {

	@Autowired
	private BasePollErrorHisMapper basePollErrorHisMapper;
	
	@Override
	public BasePollErrorHis get(PollErrorHis pollErrorHis) {
		return this.get(pollErrorHis.getId());
	}
	
	@Override
	public BasePollErrorHis get(String id) {
		return basePollErrorHisMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert PollErrorHis pollErrorHis) {
		return basePollErrorHisMapper.insert(pollErrorHis);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends PollErrorHis> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += basePollErrorHisMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate PollErrorHis pollErrorHis) {
		return basePollErrorHisMapper.update(pollErrorHis);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(PollErrorHis pollErrorHis) {
		return this.delete(pollErrorHis.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return basePollErrorHisMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return basePollErrorHisMapper.deleteByIds(ids);
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
	public List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis) {
		return basePollErrorHisMapper.findList(basePollErrorHis);
	}
	
	@Override
	public List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, String... orderByStrArray) {
		return this.findList(basePollErrorHis, new Pagination(orderByStrArray));
	}

	@Override
	public List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, Order... orders) {
		return this.findList(basePollErrorHis, new Pagination(orders));
	}

	@Override
	public List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, List<Order> orders) {
		return this.findList(basePollErrorHis, new Pagination(orders));
	}
	
	private List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(basePollErrorHis, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BasePollErrorHis basePollErrorHis) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(basePollErrorHis, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize) {
		return this.findPage(basePollErrorHis, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(basePollErrorHis, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(basePollErrorHis, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BasePollErrorHis basePollErrorHis, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(basePollErrorHis, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BasePollErrorHis basePollErrorHis, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BasePollErrorHis> list = basePollErrorHisMapper.findList(basePollErrorHis);
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