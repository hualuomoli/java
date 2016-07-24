package com.github.hualuomoli.base.annotation.persistent;

/**
 * 持久化类型
 * @author hualuomoli
 *
 */
public enum Type {

	INSERT(), // 新增
	UPDATE(), // 修改
	DELETE(), // 逻辑删除
	BATCH_INSERT() // 批量新增
	;

}
