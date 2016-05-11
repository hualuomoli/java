package com.github.hualuomoli.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.base.util.BaseUtils;
import com.github.hualuomoli.base.util.BaseUtils.ListSplit;
import com.github.hualuomoli.demo.entity.CreatorDemo;
import com.github.hualuomoli.demo.mapper.CreatorDemoMapper;

// #CreatorDemo
@Service(value = "com.github.hualuomoli.demo.service.CreatorDemoServiceImpl")
public class CreatorDemoServiceImpl implements CreatorDemoService {

	@Autowired
	private CreatorDemoMapper creatorDemoMapper;
	
	@Override
	public CreatorDemo get(String id) {
		return creatorDemoMapper.get(id);
	}

	@Override
	public void insert(CreatorDemo creatorDemo) {
		creatorDemo.preInsert();
		creatorDemoMapper.insert(creatorDemo);
	}
	
	@Override
	public void batchInsert(List<CreatorDemo> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		
		BaseUtils.preBatchInsert(list);

		ListSplit listSplit = new ListSplit(BaseUtils.getBatchMaxCount());
		while (true) {
			List<CreatorDemo> newList = BaseUtils.getList(list, listSplit);
			if (newList.size() == 0) {
				break;
			}
			creatorDemoMapper.batchInsert(newList);
		}
	}

	@Override
	public void update(CreatorDemo creatorDemo) {
		creatorDemo.preUpdate();
		creatorDemoMapper.update(creatorDemo);
	}

	@Override
	public void delete(String id) {
		creatorDemoMapper.delete(id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		creatorDemoMapper.deleteByIds(ids);
	}
	
	@Override
	public void deleteByIds(Collection<String> ids) {
		creatorDemoMapper.deleteByIds(ids);
	}

	@Override
	public List<CreatorDemo> findList(CreatorDemo creatorDemo) {
		return creatorDemoMapper.findList(creatorDemo);
	}
	
	@Override
	public Pagination findPage(CreatorDemo creatorDemo, Pagination pagination) {
		creatorDemo.setPagination(pagination);
		pagination.setDataList(creatorDemoMapper.findList(creatorDemo));
		return pagination;
	}
	
}
