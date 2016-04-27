package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.mocha;

import java.util.Map;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Resource;
import org.raml.model.parameter.QueryParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.transfer.util.TransferUtils;

/**
 * mocha转换器
 * @author hualuomoli
 *
 */
public class GetTransfer extends MochaTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		return TransferUtils.isGet(action, requestMimeType);
	}

	@Override
	public void addRequestParameter(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			String parentFullUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// 参数
		if (action.getQueryParameters() == null || action.getQueryParameters().size() == 0) {
			return;
		}
		Map<String, QueryParameter> queryParameters = action.getQueryParameters();
		for (QueryParameter queryParameter : queryParameters.values()) {
			buffer.append(LINE).append(TAB).append(TAB).append(TAB);
			buffer.append(".query(");
			// key
			buffer.append("'");
			buffer.append(queryParameter.getDisplayName());
			buffer.append("='");
			// value 防止中文乱码
			buffer.append(" + encodeURIComponent");
			buffer.append("('");
			buffer.append(queryParameter.getExample());
			buffer.append("')");

			buffer.append(")");
		}
	}

}
