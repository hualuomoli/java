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
import com.github.hualuomoli.extend.base.entity.BaseProduct;
import com.github.hualuomoli.extend.entity.Product;
import com.github.hualuomoli.extend.base.mapper.BaseProductMapper;
import com.github.hualuomoli.extend.base.service.BaseProductService;
import com.github.hualuomoli.exception.MoreDataFoundException;

// #BaseProduct
@Service(value = "com.github.hualuomoli.extend.base.service.BaseProductServiceImpl")
@Transactional(readOnly = true)
public class BaseProductServiceImpl implements BaseProductService {

	@Autowired
	private BaseProductMapper baseProductMapper;
	
	@Override
	public BaseProduct get(Product product) {
		return this.get(product.getId());
	}
	
	@Override
	public BaseProduct get(String id) {
		return baseProductMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PreInsert Product product) {
		return baseProductMapper.insert(product);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <T extends Product> int batchInsert(@PreBatchInsert  List<T> list) {
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
			count += baseProductMapper.batchInsert(newList);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PreUpdate Product product) {
		return baseProductMapper.update(product);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(@PreDelete Product product) {
		return baseProductMapper.update(product);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		Product temp = new Product();
		temp.setId(id);
		return this.logicalDelete(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Product product) {
		return this.delete(product.getId());
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseProductMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		return baseProductMapper.deleteByIds(ids);
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
	public List<BaseProduct> findList(BaseProduct baseProduct) {
		return baseProductMapper.findList(baseProduct);
	}
	
	@Override
	public List<BaseProduct> findList(BaseProduct baseProduct, String... orderByStrArray) {
		return this.findList(baseProduct, new Pagination(orderByStrArray));
	}

	@Override
	public List<BaseProduct> findList(BaseProduct baseProduct, Order... orders) {
		return this.findList(baseProduct, new Pagination(orders));
	}

	@Override
	public List<BaseProduct> findList(BaseProduct baseProduct, List<Order> orders) {
		return this.findList(baseProduct, new Pagination(orders));
	}
	
	private List<BaseProduct> findList(BaseProduct baseProduct, Pagination pagination) {
		pagination.setQueryType(QueryType.ONLY_DATA);
		Page page = this.findPage(baseProduct, pagination);
		return page.getDataList();
	}
	
	@Override
	public Integer getTotal(BaseProduct baseProduct) {
		Pagination pagination = new Pagination(QueryType.ONLY_COUNT);
		Page page = this.findPage(baseProduct, pagination);
		return page.getCount();
	}
	
	@Override
	public Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize) {
		return this.findPage(baseProduct, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseProduct, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseProduct, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseProduct baseProduct, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseProduct, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseProduct baseProduct, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseProduct> list = baseProductMapper.findList(baseProduct);
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
