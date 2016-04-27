package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

/**
 * 请求参数为Restful
 * @author hualuomoli
 *
 */
public class RestfulTransfer extends JavaTransfer {

	/**
	 * 请求方式: GET/DELETE
	 * 请求协议: *
	 * 请求参数: query查询参数0个,form表单参数数0个
	 */
	@Override
	public boolean support(Action action, MimeType requestMimeType) {

		// 请求方式: GET/DELETE
		if (action.getType() != ActionType.GET && action.getType() != ActionType.DELETE) {
			return false;
		}

		// 请求协议: *

		// 请求参数: query查询参数0个,form表单参数大于等于1个参数
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}
		// form
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
