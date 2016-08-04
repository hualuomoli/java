package com.github.hualuomoli.demo.creator.base.service.impl;

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
import com.github.hualuomoli.demo.creator.base.entity.BaseRegion;
import com.github.hualuomoli.demo.creator.base.mapper.BaseRegionMapper;
import com.github.hualuomoli.demo.creator.base.service.BaseRegionService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseRegion
@Service(value = "com.github.hualuomoli.demo.creator.base.service.BaseRegionServiceImpl")
@Transactional(readOnly = true)
public class BaseRegionServiceImpl implements BaseRegionService {

	@Autowired
	private BaseRegionMapper baseRegionMapper;
	
	@Override
	public BaseRegion get(BaseRegion baseRegion) {
		return this.get(baseRegion.getId());
	}
	
	@Override
	public BaseRegion get(String id) {
		return baseRegionMapper.get(id);
	}
	
	@Override
	public BaseRegion getUnique(
		java.lang.String code,
		java.lang.Integer type
	) {
		BaseRegion baseRegion = new BaseRegion();
		baseRegion.setCode(code);
		baseRegion.setType(type);
		List<BaseRegion> list = this.findList(baseRegion);
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
	public int insert(@PreInsert BaseRegion baseRegion) {
		return baseRegionMapper.insert(baseRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseRegion> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseRegion> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseRegionMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseRegion baseRegion) {
		return baseRegionMapper.update(baseRegion);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete BaseRegion baseRegion) {
		return baseRegionMapper.update(baseRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		BaseRegion temp = new BaseRegion();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(BaseRegion baseRegion) {
		return this.delete(baseRegion.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseRegionMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseRegionMapper.deleteByIds(ids);
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
	public List<BaseRegion> findList(BaseRegion baseRegion) {
		return baseRegionMapper.findList(baseRegion);
	}
	
	@Override
	public List<BaseRegion> findList(BaseRegion baseRegion, String... orderByStrArray) {
		return this.findList(baseRegion, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseRegion> findList(BaseRegion baseRegion, Order... orders) {
		return this.findList(baseRegion, new Pagination(orders));
	}

	@Override
	public List<BaseRegion> findList(BaseRegion baseRegion, List<Order> orders) {
		return this.findList(baseRegion, new Pagination(orders));
	}
	
	private List<BaseRegion> findList(BaseRegion baseRegion, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseRegion, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseRegion baseRegion) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseRegion, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize) {
		return this.findPage(baseRegion, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseRegion, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseRegion, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseRegion baseRegion, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseRegion, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseRegion baseRegion, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseRegion> list = baseRegionMapper.findList(baseRegion);
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
