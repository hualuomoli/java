package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;

// #BaseRoleMenu
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseRoleMenuMapper")
public interface BaseRoleMenuMapper {

	
	BaseRoleMenu get(String id);

	int insert(BaseRoleMenu baseRoleMenu);
	
	int batchInsert(@Param(value = "list") List<BaseRoleMenu> list);

	int update(BaseRoleMenu baseRoleMenu);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseRoleMenu> findList(BaseRoleMenu baseRoleMenu);

}
