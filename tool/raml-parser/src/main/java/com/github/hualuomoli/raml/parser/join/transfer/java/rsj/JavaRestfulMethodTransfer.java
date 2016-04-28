package com.github.hualuomoli.raml.parser.join.transfer.java.rsj;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为Restful
 * @author hualuomoli
 *
 */
public class JavaRestfulMethodTransfer extends RSJJavaDefaultMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isRestful(action, requestMimeType);
	}

	@Override
	protected String getQueryEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		return null;
	}

}
