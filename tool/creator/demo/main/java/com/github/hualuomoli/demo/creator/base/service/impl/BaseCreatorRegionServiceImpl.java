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
import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorRegion;
import com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorRegionMapper;
import com.github.hualuomoli.demo.creator.base.service.BaseCreatorRegionService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseCreatorRegion
@Service(value = "com.github.hualuomoli.demo.creator.base.service.BaseCreatorRegionServiceImpl")
@Transactional(readOnly = true)
public class BaseCreatorRegionServiceImpl implements BaseCreatorRegionService {

	@Autowired
	private BaseCreatorRegionMapper baseCreatorRegionMapper;
	
	@Override
	public BaseCreatorRegion get(BaseCreatorRegion baseCreatorRegion) {
		return this.get(baseCreatorRegion.getId());
	}
	
	@Override
	public BaseCreatorRegion get(String id) {
		return baseCreatorRegionMapper.get(id);
	}
	
	@Override
	public BaseCreatorRegion getUnique(
		java.lang.String code,
		java.lang.Integer type
	) {
		BaseCreatorRegion baseCreatorRegion = new BaseCreatorRegion();
		baseCreatorRegion.setCode(code);
		baseCreatorRegion.setType(type);
		List<BaseCreatorRegion> list = this.findList(baseCreatorRegion);
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
	public int insert(@PreInsert BaseCreatorRegion baseCreatorRegion) {
		return baseCreatorRegionMapper.insert(baseCreatorRegion);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PreBatchInsert  List<BaseCreatorRegion> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		Integer count = 0;
		Config config = new Config(100);
		while (true) {
			List<BaseCreatorRegion> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			count += baseCreatorRegionMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate BaseCreatorRegion baseCreatorRegion) {
		return baseCreatorRegionMapper.update(baseCreatorRegion);
	}


	@Override
	@Transactional(readOnly = false)
	public int delete(BaseCreatorRegion baseCreatorRegion) {
		return this.delete(baseCreatorRegion.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseCreatorRegionMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseCreatorRegionMapper.deleteByIds(ids);
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
	public List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion) {
		return baseCreatorRegionMapper.findList(baseCreatorRegion);
	}
	
	@Override
	public List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, String... orderByStrArray) {
		return this.findList(baseCreatorRegion, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, Order... orders) {
		return this.findList(baseCreatorRegion, new Pagination(orders));
	}

	@Override
	public List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, List<Order> orders) {
		return this.findList(baseCreatorRegion, new Pagination(orders));
	}
	
	private List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseCreatorRegion, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseCreatorRegion baseCreatorRegion) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseCreatorRegion, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize) {
		return this.findPage(baseCreatorRegion, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseCreatorRegion, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseCreatorRegion, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseCreatorRegion baseCreatorRegion, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseCreatorRegion, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseCreatorRegion baseCreatorRegion, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseCreatorRegion> list = baseCreatorRegionMapper.findList(baseCreatorRegion);
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
