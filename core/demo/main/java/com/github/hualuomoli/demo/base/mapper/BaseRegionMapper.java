package com.github.hualuomoli.demo.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.base.entity.BaseRegion;
import com.github.hualuomoli.demo.entity.Region;

// #BaseRegion
@Repository(value = "com.github.hualuomoli.demo.base.mapper.BaseRegionMapper")
public interface BaseRegionMapper {

	
	BaseRegion get(String id);

	int insert(Region region);
	
	<T extends Region> int batchInsert(@Param(value = "list") List<T> list);

	int update(Region region);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseRegion> findList(BaseRegion baseRegion);

}
