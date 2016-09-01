package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.entity.RoleMenu;

// #BaseRoleMenu
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseRoleMenuMapper")
public interface BaseRoleMenuMapper {

	
	BaseRoleMenu get(String id);

	int insert(RoleMenu roleMenu);
	
	<T extends RoleMenu> int batchInsert(@Param(value = "list") List<T> list);

	int update(RoleMenu roleMenu);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu);

}
