package com.github.hualuomoli.plugin.roll.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.plugin.roll.base.entity.BasePollFrequency;
import com.github.hualuomoli.plugin.roll.entity.PollFrequency;

// #BasePollFrequency
@Repository(value = "com.github.hualuomoli.plugin.roll.base.mapper.BasePollFrequencyMapper")
public interface BasePollFrequencyMapper {

	
	BasePollFrequency get(String id);

	int insert(PollFrequency pollFrequency);
	
	<T extends PollFrequency> int batchInsert(@Param(value = "list") List<T> list);

	int update(PollFrequency pollFrequency);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BasePollFrequency> findList(BasePollFrequency basePollFrequency);

}
