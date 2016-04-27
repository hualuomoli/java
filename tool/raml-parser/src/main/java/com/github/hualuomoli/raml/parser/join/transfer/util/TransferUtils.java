package com.github.hualuomoli.raml.parser.join.transfer.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.FormParameter;

import com.github.hualuomoli.raml.parser.exception.ParseException;
import com.github.hualuomoli.raml.parser.join.transfer.Transfer;

/**
 * 转换器工具
 * @author hualuomoli
 *
 */
public class TransferUtils {

	// restful
	public static boolean isRestful(Action action, MimeType requestMimeType) {
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

	// get
	public static boolean isGet(Action action, MimeType requestMimeType) {
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

	// UrlEncoded
	public static boolean isUrlEncoded(Action action, MimeType requestMimeType) {
		// 请求方式: POST
		if (action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: application/x-www-form-urlencoded
		if (requestMimeType == null || !StringUtils.equalsIgnoreCase(requestMimeType.getType(), Transfer.MIME_TYPE_URLENCODED)) {
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

	// JSON
	public static boolean isJSON(Action action, MimeType requestMimeType) {
		// 请求方式: POST
		if (action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: application/json
		if (requestMimeType == null || !StringUtils.equalsIgnoreCase(requestMimeType.getType(), Transfer.MIME_TYPE_JSON)) {
			return false;
		}

		// 请求参数: query查询参数0个,form表单参于0个
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}
		// form
		if (requestMimeType != null && requestMimeType.getFormParameters() != null && requestMimeType.getFormParameters().size() > 0) {
			return false;
		}

		// 请求例子: 不为空
		if (requestMimeType == null || StringUtils.isEmpty(requestMimeType.getExample())) {
			return false;
		}

		return true;
	}

	// File
	public static boolean isFile(Action action, MimeType requestMimeType) throws ParseException {
		// 请求方式: POST
		if (action.getType() == null || action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: multipart/form-data
		if (requestMimeType == null || !StringUtils.equals(requestMimeType.getType(), Transfer.MIME_TYPE_MULTIPART)) {
			return false;
		}

		// 请求参数: query查询参数0个,form表单参于多于0个,并且参数中有一个参数的类型是文件类型
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}

		// form
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return false;
		}

		// type
		boolean ok = false;
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();
		valid: for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					ok = true;
					break valid;
				}
			}
		}

		if (!ok) {
			throw new ParseException("there is no file type parameter, please use urlencoded.");
		}

		return true;
	}

}
