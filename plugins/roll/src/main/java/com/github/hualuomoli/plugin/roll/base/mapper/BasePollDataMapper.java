package com.github.hualuomoli.plugin.roll.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.plugin.roll.base.entity.BasePollData;
import com.github.hualuomoli.plugin.roll.entity.PollData;

// #BasePollData
@Repository(value = "com.github.hualuomoli.plugin.roll.base.mapper.BasePollDataMapper")
public interface BasePollDataMapper {

	
	BasePollData get(String id);

	int insert(PollData pollData);
	
	<T extends PollData> int batchInsert(@Param(value = "list") List<T> list);

	int update(PollData pollData);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BasePollData> findList(BasePollData basePollData);

}
