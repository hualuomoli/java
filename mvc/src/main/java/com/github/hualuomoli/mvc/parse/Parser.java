
package com.github.hualuomoli.mvc.parse;

import com.github.hualuomoli.mvc.parse.exception.ParseException;

/**
 * MVC 参数解析
 * @author hualuomoli
 *
 */
public interface Parser {

	/**
	 * 解析
	 * @param dest 目标
	 * @param data 数据
	 */
	void parse(Object dest, String data) throws ParseException;

}
