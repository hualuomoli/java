package com.github.hualuomoli.raml.join.adaptor.entity;

/**
 * 参数数据类型
 * @author hualuomoli
 *
 */
public enum DataType {

	OBJECT(), // 对象
	ARRAY(), // 数组
	SIMPLE() // 简单类型
	;
	//

	private DataType() {
	}

}
