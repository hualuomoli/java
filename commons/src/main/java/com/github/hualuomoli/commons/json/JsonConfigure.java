package com.github.hualuomoli.commons.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON输出配置
 * @author liubaoquan
 *
 */
public interface JsonConfigure {

	/**
	 * 配置
	 * @param objectMapper
	 */
	void config(ObjectMapper objectMapper);

}
