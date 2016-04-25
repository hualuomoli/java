package com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.file;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.UriParameter;

import com.github.hualuomoli.raml.parser.join.java.transfer.res.success.json.ResponseSuccessJsonTransfer;

/**
 * 请求参数为multipart/form-data
 * @author hualuomoli
 *
 */
public class FileTransfer extends ResponseSuccessJsonTransfer {

	/**
	 * 请求方式: POST
	 * 请求协议: multipart/form-data
	 * 请求参数: query查询参数0个,form表单参于多于0个,并且参数中有一个参数的类型是文件类型
	 */
	@Override
	public boolean support(Action action, MimeType queryMimeType) {

		// 请求方式: POST
		if (action.getType() == null || action.getType() != ActionType.POST) {
			return false;
		}

		// 请求协议: multipart/form-data
		if (queryMimeType == null || !StringUtils.equals(queryMimeType.getType(), MIME_TYPE_MULTIPART)) {
			return false;
		}

		// 请求参数: query查询参数0个,form表单参于多于0个,并且参数中有一个参数的类型是文件类型
		// query
		if (action.getQueryParameters() != null && action.getQueryParameters().size() > 0) {
			return false;
		}

		// form
		if (queryMimeType == null || queryMimeType.getFormParameters() == null || queryMimeType.getFormParameters().size() == 0) {
			return false;
		}

		// type
		boolean ok = false;
		Map<String, List<FormParameter>> formParameters = queryMimeType.getFormParameters();
		valid: for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			if (values == null || values.size() == 0) {
				return false;
			}
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					ok = true;
					break valid;
				}
			}
		}

		if (!ok) {
			return false;
		}

		return true;
	}

	@Override
	public String getData(MimeType queryMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		// TODO
		return "// 文件上传";
	}

}
