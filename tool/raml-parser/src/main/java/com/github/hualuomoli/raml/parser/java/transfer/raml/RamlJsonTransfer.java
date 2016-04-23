package com.github.hualuomoli.raml.parser.java.transfer.raml;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.java.transfer.JavaJoinTransfer;
import com.github.hualuomoli.raml.parser.transfer.Transfer;
import com.google.common.collect.Lists;

/**
 * RAML -> JSON
 * 请求参数为RAML
 * 响应类型为JSON
 * @author hualuomoli
 *
 */
public class RamlJsonTransfer extends JavaJoinTransfer implements Transfer {

	@Override
	public boolean support(Action action, MimeType queryMimeType, String status, MimeType responseMimeType) {
		if (!(action.getType() == ActionType.GET || action.getType() == ActionType.DELETE)) {
			return false;
		}
		// query 参数为空
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}
		// form 参数为空
		if (queryMimeType != null && queryMimeType.getFormParameters() != null && queryMimeType.getFormParameters().size() > 0) {
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
