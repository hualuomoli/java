package com.github.hualuomoli.raml.parser.join.transfer.res.success.json.java;

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

/**
 * 请求参数为multipart/form-data
 * @author hualuomoli
 *
 */
public class FileTransfer extends JavaTransfer {

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

	// 其他参数
	@Override
	public void addMethodOtherParameterNote(StringBuilder buffer, MimeType requestMimeType, String status, MimeType responseMimeType, Action action,
			String relativeUri, Map<String, UriParameter> parentFullUriParameters, Resource resource) {

		// add super
		super.addMethodOtherParameterNote(buffer, requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource);

		// @param photo 头像
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return;
		}
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();

		for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					buffer.append(LINE).append(TAB).append(" * ").append("@param ");
					buffer.append(formParameter.getDisplayName()).append(" ").append(formParameter.getDescription());
					buffer.append(", ");
				}
			}
		}
	}

	@Override
	public String getMethodOtherParameterHeader(MimeType requestMimeType, String status, MimeType responseMimeType, Action action, String relativeUri,
			Map<String, UriParameter> parentFullUriParameters, Resource resource) {
		StringBuilder buffer = new StringBuilder();

		// add super
		buffer.append(super.getMethodOtherParameterHeader(requestMimeType, status, responseMimeType, action, relativeUri, parentFullUriParameters, resource));

		// @RequestParam(value = "photo") File photo
		if (requestMimeType == null || requestMimeType.getFormParameters() == null || requestMimeType.getFormParameters().size() == 0) {
			return buffer.toString();
		}
		Map<String, List<FormParameter>> formParameters = requestMimeType.getFormParameters();

		for (String displayName : formParameters.keySet()) {
			List<FormParameter> values = formParameters.get(displayName);
			for (FormParameter formParameter : values) {
				if (formParameter.getType() == ParamType.FILE) {
					buffer.append("@RequestParam");
					buffer.append("(");
					buffer.append("value = ").append(QUOTES).append(displayName).append(QUOTES);
					buffer.append(", required = ").append(formParameter.isRequired() ? "true" : "false");
					buffer.append(")");

					buffer.append(" MultipartFile ").append(displayName);

					buffer.append(", ");
				}
			}
		}

		return buffer.toString();
	}

	@Override
	public String getEntityName(Action action, String relativeUri) {
		return "file" + super.getEntityName(action, relativeUri);
	}

}
