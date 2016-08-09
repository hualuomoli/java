package com.github.hualuomoli.system.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.system.base.entity.BaseUserRole;

// #BaseUserRole
@Repository(value = "com.github.hualuomoli.system.base.mapper.BaseUserRoleMapper")
public interface BaseUserRoleMapper {

	
	BaseUserRole get(String id);

	int insert(BaseUserRole baseUserRole);
	
	int batchInsert(@Param(value = "list") List<BaseUserRole> list);

	int update(BaseUserRole baseUserRole);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseUserRole> findList(BaseUserRole baseUserRole);

}
