package com.github.hualuomoli.base.annotation.persistent;

/**
 * 持久化类型
 * @author hualuomoli
 *
 */
public enum Type {

	INSERT(), // 新增
	UPDATE(), // 修改
	LOGICAL_DELETE(), // 逻辑删除(不删除数据,标识数据为已删除状态)
	BATCH_INSERT() // 批量新增
	;

}
