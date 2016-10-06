package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.entity.UserRole;

// #BaseUserRole
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseUserRoleMapper")
public interface BaseUserRoleMapper {

	
	BaseUserRole get(String id);

	int insert(UserRole userRole);
	
	<T extends UserRole> int batchInsert(@Param(value = "list") List<T> list);

	int update(UserRole userRole);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseUserRole> findList(BaseUserRole baseUserRole);

}
