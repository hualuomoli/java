package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.raml;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * 请求参数为RAML
 * @author hualuomoli
 *
 */
public class RamlJsonTransfer extends ResponseSuccessJsonTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		if (!(action.getType() == ActionType.GET || action.getType() == ActionType.DELETE)) {
			return false;
		}
		// query 参数为空
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}
		// form 参数为空
		if (requestMimeType != null && requestMimeType.getFormParameters() != null && requestMimeType.getFormParameters().size() > 0) {
			return false;
		}

		return true;
	}

	// 不实用entity
	@Override
	public String getEntityName(Action action, String relativeUri) {
		return null;
	}

}
