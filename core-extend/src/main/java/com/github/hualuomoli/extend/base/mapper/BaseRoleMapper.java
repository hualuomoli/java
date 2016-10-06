package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.entity.Role;

// #BaseRole
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseRoleMapper")
public interface BaseRoleMapper {

	
	BaseRole get(String id);

	int insert(Role role);
	
	<T extends Role> int batchInsert(@Param(value = "list") List<T> list);

	int update(Role role);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseRole> findList(BaseRole baseRole);

}
