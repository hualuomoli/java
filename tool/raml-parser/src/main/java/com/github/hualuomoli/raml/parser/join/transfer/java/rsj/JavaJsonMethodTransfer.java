package com.github.hualuomoli.raml.parser.join.transfer.java.rsj;

import java.util.Map;

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

}
