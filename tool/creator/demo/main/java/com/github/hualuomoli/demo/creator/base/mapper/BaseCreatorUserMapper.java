package com.github.hualuomoli.demo.creator.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorUser;

// #BaseCreatorUser
@Repository(value = "com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorUserMapper")
public interface BaseCreatorUserMapper {

	
	BaseCreatorUser get(String id);

	int insert(BaseCreatorUser baseCreatorUser);
	
	int batchInsert(@Param(value = "list") List<BaseCreatorUser> list);

	int update(BaseCreatorUser baseCreatorUser);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseCreatorUser> findList(BaseCreatorUser baseCreatorUser);

}
