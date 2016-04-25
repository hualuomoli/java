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
 * 请求参数为JSON
 * @author hualuomoli
 *
 */
public class JsonTransfer extends ResponseSuccessJsonTransfer {

	/**
	 * 请求方式: POST
	 * 请求协议: application/json
	 * 请求参数: query查询参数0个,form表单参于0个
	 * 请求例子: 不为空
	 */
	@Override
	public boolean support(Action action, MimeType queryMimeType) {

		// 请求方式: POST
		if (action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: application/json
		if (queryMimeType == null || !StringUtils.equalsIgnoreCase(queryMimeType.getType(), MIME_TYPE_JSON)) {
			return false;
		}

		// 请求参数: query查询参数0个,form表单参于0个
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}
		// form
		if (queryMimeType != null && queryMimeType.getFormParameters() != null && queryMimeType.getFormParameters().size() > 0) {
			return false;
		}

		// 请求例子: 不为空
		if (queryMimeType == null || StringUtils.isEmpty(queryMimeType.getExample())) {
			return false;
		}

		return true;
	}

	@Override
	public String getMethodOtherParameterHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		// User user
		StringBuilder buffer = new StringBuilder();

		String entityName = this.getEntityName(action, relativeUri);
		if (StringUtils.isEmpty(entityName)) {
			return buffer.toString();
		}

		String entityUpperName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
		String entityLowerName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

		buffer.append(entityUpperName).append("@ResponseBody ").append(entityLowerName).append(", ");

		return buffer.toString();

	}

	@Override
	public String getEntityName(Action action, String relativeUri) {
		return "json" + super.getEntityName(action, relativeUri);
	}

}
