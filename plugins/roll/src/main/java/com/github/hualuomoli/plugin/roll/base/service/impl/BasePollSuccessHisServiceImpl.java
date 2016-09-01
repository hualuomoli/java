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
import com.github.hualuomoli.plugin.roll.base.entity.BasePollSuccessHis;
import com.github.hualuomoli.plugin.roll.entity.PollSuccessHis;
import com.github.hualuomoli.plugin.roll.base.mapper.BasePollSuccessHisMapper;
import com.github.hualuomoli.plugin.roll.base.service.BasePollSuccessHisService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BasePollSuccessHis
@Service(value = "com.github.hualuomoli.plugin.roll.base.service.BasePollSuccessHisServiceImpl")
@Transactional(readOnly = true)
public class BasePollSuccessHisServiceImpl implements BasePollSuccessHisService {

	@Autowired
	private BasePollSuccessHisMapper basePollSuccessHisMapper;
	
	@Override
	public BasePollSuccessHis get(PollSuccessHis pollSuccessHis) {
		return this.get(pollSuccessHis.getId());
	}
	
	@Override
	public BasePollSuccessHis get(String id) {
		return basePollSuccessHisMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert PollSuccessHis pollSuccessHis) {
		return basePollSuccessHisMapper.insert(pollSuccessHis);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends PollSuccessHis> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += basePollSuccessHisMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate PollSuccessHis pollSuccessHis) {
		return basePollSuccessHisMapper.update(pollSuccessHis);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(PollSuccessHis pollSuccessHis) {
		return this.delete(pollSuccessHis.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return basePollSuccessHisMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return basePollSuccessHisMapper.deleteByIds(ids);
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
	public List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis) {
		return basePollSuccessHisMapper.findList(basePollSuccessHis);
	}
	
	@Override
	public List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, String... orderByStrArray) {
		return this.findList(basePollSuccessHis, new Pagination(orderByStrArray));
	}

	@Override
	public List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, Order... orders) {
		return this.findList(basePollSuccessHis, new Pagination(orders));
	}

	@Override
	public List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, List<Order> orders) {
		return this.findList(basePollSuccessHis, new Pagination(orders));
	}
	
	private List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(basePollSuccessHis, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BasePollSuccessHis basePollSuccessHis) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(basePollSuccessHis, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize) {
		return this.findPage(basePollSuccessHis, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(basePollSuccessHis, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(basePollSuccessHis, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BasePollSuccessHis basePollSuccessHis, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(basePollSuccessHis, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BasePollSuccessHis basePollSuccessHis, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BasePollSuccessHis> list = basePollSuccessHisMapper.findList(basePollSuccessHis);
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
