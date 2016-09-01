package com.github.hualuomoli.plugin.roll.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "com.github.hualuomoli.plugin.roll.mapper.DefaultRollMapper")
public interface DefaultRollMapper {

	/**
	 * 锁定数据
	 * @param unLockStr	     未锁定字符串
	 * @param lockString  数据锁的字符串
	 * @param executeTime 执行时间
	 * @param lockTime	      数据锁定时间
	 * @param count		     数据个数
	 */
	int lock(@Param(value = "unLockStr") String unLockStr, @Param(value = "lockString") String lockString, @Param(value = "executeTime") Date executeTime,
			@Param(value = "lockTime") Date lockTime, @Param(value = "count") Integer count);

}
