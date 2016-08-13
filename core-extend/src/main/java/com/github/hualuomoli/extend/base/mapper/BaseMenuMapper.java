package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseMenu;

// #BaseMenu
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseMenuMapper")
public interface BaseMenuMapper {

	
	BaseMenu get(String id);

	int insert(BaseMenu baseMenu);
	
	int batchInsert(@Param(value = "list") List<BaseMenu> list);

	int update(BaseMenu baseMenu);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseMenu> findList(BaseMenu baseMenu);

}
