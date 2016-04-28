package com.github.hualuomoli.raml.parser.join.transfer.mocha.rsj;

import java.util.List;
import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * URLEncoded转换器
 * @author hualuomoli
 *
 */
public class MochaUrlEncodedMethodTransfer extends RSJMochaMethodTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isUrlEncoded(action, requestMimeType);
	}

	@Override
	public void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return;
		}
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
		for (String displayName : formParameters.keySet()) {
			List<FormParameter> list = formParameters.get(displayName);
			for (FormParameter formParameter : list) {
				buffer.append(LINE).append(TAB).append(TAB).append(TAB);
				buffer.append(".send(");
				// key
				buffer.append("'");
				buffer.append(formParameter.getDisplayName());
				buffer.append("=");
				buffer.append(formParameter.getExample());
				buffer.append("'");

				buffer.append(")");

			}
		}
	}

}
