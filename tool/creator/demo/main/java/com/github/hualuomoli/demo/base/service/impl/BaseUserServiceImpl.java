package com.github.hualuomoli.demo.base.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.annotation.persistent.PrePersistent;
import com.github.hualuomoli.base.annotation.persistent.Type;
import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.base.entity.Page;
import com.github.hualuomoli.base.exceptione.MoreDataFoundException;
import com.github.hualuomoli.base.plugin.mybatis.entity.Order;
import com.github.hualuomoli.base.plugin.mybatis.entity.Pagination;
import com.github.hualuomoli.base.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.commons.util.CollectionUtils;
import com.github.hualuomoli.commons.util.CollectionUtils.Config;
import com.github.hualuomoli.demo.base.entity.BaseUser;
import com.github.hualuomoli.demo.base.mapper.BaseUserMapper;
import com.github.hualuomoli.demo.base.service.BaseUserService;


// #BaseUser
@Service(value = "com.github.hualuomoli.demo.base.service.BaseUserServiceImpl")
@Transactional(readOnly = true)
public class BaseUserServiceImpl implements BaseUserService {

	@Autowired
	private BaseUserMapper baseUserMapper;
	
	@Override
	public BaseUser get(BaseUser baseUser) {
		return baseUserMapper.get(baseUser);
	}
	
	@Override
	public BaseUser get(String id) {
		return baseUserMapper.get(id);
	}
	

	@Override
	@Transactional(readOnly = false)
	public int insert(@PrePersistent(type = Type.INSERT) BaseUser baseUser) {
		return baseUserMapper.insert(baseUser);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int batchInsert(@PrePersistent(type = Type.BATCH_INSERT)  List<BaseUser> list) {
		if (list == null || list.size() == 0) {
			return 0;
		}	
		
		Config config = new Config(100);
		while (true) {
			List<BaseUser> newList = CollectionUtils.fetchDatas(list, config);
			if (newList.size() == 0) {
				break;
			}
			baseUserMapper.batchInsert(newList);
		}
		return list.size();
	}

	@Override
	@Transactional(readOnly = false)
	public int update(@PrePersistent(type = Type.UPDATE)  BaseUser baseUser) {
		return baseUserMapper.update(baseUser);
	}

	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(BaseUser baseUser) {
		BaseUser temp = new BaseUser();
		temp.setId(baseUser.getId());
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int logicalDelete(String id) {
		BaseUser temp = new BaseUser();
		temp.setId(id);
		temp.setStatus(Status.DELETED.getValue());
		return this.update(temp);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(BaseUser baseUser) {
		return baseUserMapper.delete(baseUser);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int delete(String id) {
		return baseUserMapper.delete(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(String[] ids) {
		return baseUserMapper.deleteByIds(ids);
	}
	
	@Override
	@Transactional(readOnly = false)
	public int deleteByIds(Collection<String> ids) {
		return baseUserMapper.deleteByIds(ids);
	}

	@Override
	public List<BaseUser> findList(BaseUser baseUser) {
		return baseUserMapper.findList(baseUser);
	}
	
	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, String... orderByStrArray) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orderByStrArray));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, Order... orders) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orders));
	}

	@Override
	public Page findPage(BaseUser baseUser, Integer pageNo, Integer pageSize, List<Order> orders) {
		return this.findPage(baseUser, new Pagination(pageNo, pageSize, orders));
	}
	
	@Override
	public Page findPage(BaseUser baseUser, Pagination pagination) {

		// set local thread
		PaginationInterceptor.pushPagination(pagination);
		// query
		List<BaseUser> list = baseUserMapper.findList(baseUser);
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
