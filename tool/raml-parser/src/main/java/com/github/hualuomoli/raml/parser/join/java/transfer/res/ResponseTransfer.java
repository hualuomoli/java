package com.github.hualuomoli.raml.parser.join.java.transfer.res;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.java.transfer.JavaTransfer;

/**
 * 响应转换器
 * @author hualuomoli
 *
 */
public abstract class ResponseTransfer extends JavaTransfer {

	public static final String RESPONSE_TYPE_VOID = "void";
	public static final String RESPONSE_TYPE_STRING = "String";

	@Override
	public String getResponseTypeName(MimeType responseMimeType) throws ParseException {
		if (responseMimeType == null) {
			return RESPONSE_TYPE_VOID;
		}
		if (StringUtils.equals(responseMimeType.getType(), MIME_TYPE_JSON)) {
			return RESPONSE_TYPE_STRING;
		}
		throw new ParseException("can not support response MimeType " + responseMimeType.getType());
	}

}
