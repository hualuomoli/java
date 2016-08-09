package com.github.hualuomoli.system.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.system.base.entity.BaseRole;

// #BaseRole
@Repository(value = "com.github.hualuomoli.system.base.mapper.BaseRoleMapper")
public interface BaseRoleMapper {

	
	BaseRole get(String id);

	int insert(BaseRole baseRole);
	
	int batchInsert(@Param(value = "list") List<BaseRole> list);

	int update(BaseRole baseRole);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseRole> findList(BaseRole baseRole);

}
