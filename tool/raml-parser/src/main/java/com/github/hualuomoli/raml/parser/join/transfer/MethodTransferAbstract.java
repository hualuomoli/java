package com.github.hualuomoli.raml.parser.join.transfer;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.Join;

/**
 * 方法转换器
 * @author hualuomoli
 *
 */
public abstract class MethodTransferAbstract implements MethodTransfer, Join {

	@Override
	public String getData(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException {

		StringBuilder buffer = new StringBuilder();
		buffer.append(LINE);

		buffer.append(this.getNote(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		buffer.append(this.getHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		buffer.append(this.getContent(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		buffer.append(this.getFooter(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		buffer.append(this.getOthers(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUri, parentFullUriParameters, resource));

		return buffer.toString();
	}

	/** 获取方法的注释 */
	public abstract String getNote(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri, String parentFullUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/** 获取方法的头部 */
	public abstract String getHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/** 获取方法体 */
	public abstract String getContent(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/** 获取方法的尾部 */
	public abstract String getFooter(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

	/** 获取方法的其他信息 */
	public abstract String getOthers(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) throws ParseException;

}
