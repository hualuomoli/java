package com.github.hualuomoli.mvc.parse.json;

import com.github.hualuomoli.commons.json.JsonMapper;
import com.github.hualuomoli.commons.util.ObjectUtils;
import com.github.hualuomoli.mvc.parse.Parser;
import com.github.hualuomoli.mvc.parse.exception.ParseException;

/**
 * JSON转换器
 * @author hualuomoli
 *
 */
public class JsonParser implements Parser {

	@Override
	public void parse(Object dest, String data) throws ParseException {
		ObjectUtils.copy(dest, JsonMapper.fromJsonString(data, dest.getClass()), true);
	}

}
