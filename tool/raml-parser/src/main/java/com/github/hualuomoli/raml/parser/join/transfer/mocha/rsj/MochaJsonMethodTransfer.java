package com.github.hualuomoli.raml.parser.join.transfer.mocha.rsj;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * JSON转换器
 * @author hualuomoli
 *
 */
public class MochaJsonMethodTransfer extends RSJMochaMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isJSON(action, requestMimeType);
	}

	@Override
	protected void addRequestOthers(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		// .set('Content-Type', 'application/json')
		buffer.append(LINE).append(TAB).append(TAB).append(TAB);
		buffer.append(".set('Content-Type', 'application/json')");

	}

	@Override
	public void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		if (requestMimeType == null || StringUtils.isEmpty(requestMimeType.getExample())) {
			return;
		}
		buffer.append(LINE).append(TAB).append(TAB).append(TAB);
		buffer.append(".send({");

		String[] array = requestMimeType.getExample().split("\n");
		// 第一行和最后一行不要
		for (int i = 1; i < array.length - 1; i++) {
			String data = array[i];
			buffer.append(LINE).append(TAB).append(TAB).append(TAB).append(TAB);
			buffer.append(data);
		}
		buffer.append(LINE).append(TAB).append(TAB).append(TAB);
		buffer.append("})");

	}

}
