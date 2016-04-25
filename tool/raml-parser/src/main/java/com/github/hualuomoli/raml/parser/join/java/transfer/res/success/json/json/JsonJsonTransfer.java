package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.json;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * JSON(POST) -> JSON
 * 请求参数为JSON
 * 响应类型为JSON
 * @author hualuomoli
 *
 */
public class JsonJsonTransfer extends ResponseSuccessJsonTransfer {

	@Override
	public boolean support(Action action, MimeType queryMimeType) {
		if (action.getType() != ActionType.POST) {
			return false;
		}
		if (queryMimeType != null && !StringUtils.equalsIgnoreCase(queryMimeType.getType(), MIME_TYPE_JSON)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean addEntityAnnonation(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		return true;
	}

}
