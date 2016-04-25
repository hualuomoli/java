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

	/**
	 * 请求方式: GET/DELETE
	 * 请求协议: *
	 * 请求参数: query查询参数不限,form表单参数0个
	 */
	@Override
	public boolean support(Action action, MimeType requestMimeType) {
		if (action.getType() != ActionType.GET) {
			return false;
		}

		// 请求协议: *

		// 请求参数: query查询参数不限,form表单参数0个
		// form
		if (requestMimeType != null && requestMimeType.getFormParameters() != null && requestMimeType.getFormParameters().size() > 0) {
			return false;
		}

		return true;
	}

	// use get prefix
	@Override
	public String getEntityName(Action action, String relativeUri) {
		return "get" + super.getEntityName(action, relativeUri);
	}

}
