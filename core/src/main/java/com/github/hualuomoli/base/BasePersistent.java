package com.github.hualuomoli.base;

/**
 * 持久化
 * @author hualuomoli
 *
 */
public interface BasePersistent {

	// 主键.可以自定义主键,如果没有定义,使用32位UUID
	String getId();

	// 主键
	void setId(String id);

}
