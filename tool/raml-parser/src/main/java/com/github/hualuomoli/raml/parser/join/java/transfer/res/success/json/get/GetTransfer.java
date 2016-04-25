package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.get;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * 请求参数为GET
 * @author hualuomoli
 *
 */
public class GetTransfer extends ResponseSuccessJsonTransfer {

	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		if (action.getType() != ActionType.GET) {
			return false;
		}
		if (requestMimeType != null) {
			return false;
		}

		return true;
	}

	// 不使用entity
	@Override
	public String getEntityName(Action action, String relativeUri) {
		return null;
	}

}
