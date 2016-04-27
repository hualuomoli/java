package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为JSON
 * @author hualuomoli
 *
 */
public class JsonTransfer extends JavaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isJSON(action, requestMimeType);
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
