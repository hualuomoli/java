package com.github.hualuomoli.plugin.roll.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.plugin.roll.base.entity.BasePollSuccessHis;
import com.github.hualuomoli.plugin.roll.entity.PollSuccessHis;

// #BasePollSuccessHis
@Repository(value = "com.github.hualuomoli.plugin.roll.base.mapper.BasePollSuccessHisMapper")
public interface BasePollSuccessHisMapper {

	
	BasePollSuccessHis get(String id);

	int insert(PollSuccessHis pollSuccessHis);
	
	<T extends PollSuccessHis> int batchInsert(@Param(value = "list") List<T> list);

	int update(PollSuccessHis pollSuccessHis);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BasePollSuccessHis> findList(BasePollSuccessHis basePollSuccessHis);

}
