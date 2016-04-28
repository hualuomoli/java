package com.github.hualuomoli.raml.parser.join.transfer.java.rsj;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * 请求参数为multipart/form-data
 * @author hualuomoli
 *
 */
public class JavaFileMethodTransfer extends RSJJavaDefaultMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) throws ParseException {
		return TransferUtils.isFile(action, requestMimeType);
	}

	@Override
	protected String getQueryEntityName(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {
		return "file"
				+ super.getQueryEntityName(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource);
	}

}
