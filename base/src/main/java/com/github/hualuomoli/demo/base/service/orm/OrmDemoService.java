package com.github.hualuomoli.demo.base.service.orm;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.entity.Pagination;
import com.github.hualuomoli.demo.base.entity.Demo;
import com.github.hualuomoli.demo.base.mapper.DemoMapper;
import com.github.hualuomoli.demo.base.service.DemoService;

@Service(value = "com.github.hualuomoli.demo.base.service.orm.OrmDemoService")
@Transactional(readOnly = true)
public class OrmDemoService implements DemoService {

	@Autowired
	private DemoMapper demoMapper;

	@Override
	public Demo get(Demo demo) {
		return demoMapper.get(demo);
	}

	@Override
	public Demo get(String id) {
		return demoMapper.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void insert(Demo demo) {
		demoMapper.insert(demo);
	}

	@Override
	@Transactional(readOnly = false)
	public void batchInsert(List<Demo> list) {
		demoMapper.batchInsert(list);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Demo demo) {
		demoMapper.update(demo);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Demo demo) {
		demoMapper.delete(demo);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		demoMapper.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(String[] ids) {
		demoMapper.deleteByIds(ids);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(Collection<String> ids) {
		demoMapper.deleteByIds(ids);
	}

	@Override
	public List<Demo> findList(Demo demo) {
		return demoMapper.findList(demo);
	}

	@Override
	public Pagination findPage(Demo demo, Pagination pagination) {
		demo.setPagination(pagination);
		List<Demo> dataList = demoMapper.findList(demo);
		pagination.setDataList(dataList);
		return pagination;
	}

}
