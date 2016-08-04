package com.github.hualuomoli.demo.creator.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.creator.base.entity.BaseCreatorRegion;

// #BaseCreatorRegion
@Repository(value = "com.github.hualuomoli.demo.creator.base.mapper.BaseCreatorRegionMapper")
public interface BaseCreatorRegionMapper {

	
	BaseCreatorRegion get(String id);

	int insert(BaseCreatorRegion baseCreatorRegion);
	
	int batchInsert(@Param(value = "list") List<BaseCreatorRegion> list);

	int update(BaseCreatorRegion baseCreatorRegion);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseCreatorRegion> findList(BaseCreatorRegion baseCreatorRegion);

}
