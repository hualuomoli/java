package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.base.entity.BaseUser;
import com.github.hualuomoli.demo.entity.User;

// #BaseUser
@Repository(value = "com.github.hualuomoli.demo.base.mapper.BaseUserMapper")
public interface BaseUserMapper {

	
	BaseUser get(String id);

	int insert(User user);
	
	<T extends User> int batchInsert(@Param(value = "list") List<T> list);

	int update(User user);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseUser> findList(BaseUser baseUser);

}
