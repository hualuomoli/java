package com.github.hualuomoli.raml.parser.join.transfer.java.rsj;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils;
import com.github.hualuomoli.raml.parser.join.util.JavaRamlUtils.MethodParam;
import com.google.common.collect.Sets;

/**
 * 请求参数为JSON
 * @author hualuomoli
 *
 */
public class JavaJsonMethodTransfer extends RSJJavaDefaultMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isJSON(action, requestMimeType);
	}

	@Override
	protected String getQueryEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		return "json"
				+ super.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
	}

	@Override
	public Set<MethodParam> getOtherParams(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		Set<MethodParam> params = Sets.newHashSet();

		String queryEntityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		if (StringUtils.isEmpty(queryEntityName)) {
			return params;
		}

		String upperQueryEntityName = queryEntityName.substring(0, 1).toUpperCase() + queryEntityName.substring(1);
		String lowerQueryEntityName = queryEntityName.substring(0, 1).toLowerCase() + queryEntityName.substring(1);

		params.add(new MethodParam("@RequestBody ", upperQueryEntityName, lowerQueryEntityName));

		return params;
	}

	@Override
	public String getQueryEntityDefinition(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		StringBuilder buffer = new StringBuilder();

		if (requestMimeType == null || !StringUtils.equals(requestMimeType.getType(), MIME_TYPE_JSON) || StringUtils.isEmpty(requestMimeType.getSchema())) {
			return buffer.toString();
		}

		String queryEntityName = this.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters,
				resource);
		if (StringUtils.isEmpty(queryEntityName)) {
			return buffer.toString();
		}

		// schema
		String schema = requestMimeType.getSchema();

		String upperResultEntityName = queryEntityName.substring(0, 1).toUpperCase() + queryEntityName.substring(1);

		// buffer.append(LINE);

		buffer.append(this.getJSONClass(1, upperResultEntityName, JavaRamlUtils.parseSchema(schema)));

		return buffer.toString();
	}

}
