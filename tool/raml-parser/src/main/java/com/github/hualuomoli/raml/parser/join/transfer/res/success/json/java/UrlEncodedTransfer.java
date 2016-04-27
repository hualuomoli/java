package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;

/**
 * 请求参数为URL Encoded
 * @author hualuomoli
 *
 */
public class UrlEncodedTransfer extends JavaTransfer {

	/**
	 * 请求方式: POST
	 * 请求协议: application/x-www-form-urlencoded
	 * 请求参数: query查询参数0个,form表单参数多于0个
	 */
	@Override
	public boolean support(Action action, MimeType requestMimeType) {

		// 请求方式: POST
		if (action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: application/x-www-form-urlencoded
		if (requestMimeType == null || !StringUtils.equalsIgnoreCase(requestMimeType.getType(), MIME_TYPE_URLENCODED)) {
			return false;
		}

		// 请求参数: query查询参数0个,form表单参数大于等于1个参数
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}

		// 请求参数: 大于等于1个参数
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return false;
		}

		return true;
	}

}
