package com.github.hualuomoli.plugin.roll.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.plugin.roll.base.entity.BasePollErrorHis;
import com.github.hualuomoli.plugin.roll.entity.PollErrorHis;

// #BasePollErrorHis
@Repository(value = "com.github.hualuomoli.plugin.roll.base.mapper.BasePollErrorHisMapper")
public interface BasePollErrorHisMapper {

	
	BasePollErrorHis get(String id);

	int insert(PollErrorHis pollErrorHis);
	
	<T extends PollErrorHis> int batchInsert(@Param(value = "list") List<T> list);

	int update(PollErrorHis pollErrorHis);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BasePollErrorHis> findList(BasePollErrorHis basePollErrorHis);

}
