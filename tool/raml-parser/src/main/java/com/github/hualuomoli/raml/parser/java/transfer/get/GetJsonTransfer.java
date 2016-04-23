package com.github.hualuomoli.raml.parser.java.transfer.get;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.java.transfer.JavaJoinTransfer;
import com.github.hualuomoli.raml.parser.transfer.Transfer;
import com.google.common.collect.Lists;

/**
 * URL Encoded -> JSON
 * 请求参数为get
 * 响应类型为JSON
 * @author hualuomoli
 *
 */
public class GetJsonTransfer extends JavaJoinTransfer implements Transfer {

	@Override
	public boolean support(Action action, MimeType queryMimeType, String status, MimeType responseMimeType) {
		if (action.getType() != ActionType.GET) {
			return false;
		}
		if (queryMimeType != null) {
			return false;
		}
		if (!StringUtils.equalsIgnoreCase(status, STATUS_SUCCESS)) {
			return false;
		}
		if (responseMimeType == null || !StringUtils.equalsIgnoreCase(responseMimeType.getType(), MIME_TYPE_JSON)) {
			return false;
		}

		return true;
	}

	@Override
	protected List<String> getSupportQueryMimeType() {
		return Lists.newArrayList(MIME_TYPE_ALL);
	}

	@Override
	protected String getEntityRequestBodyPrefix() {
		return null;
	}

}
